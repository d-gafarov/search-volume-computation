package com.vandelay.industries.searchvolumecomputation.service;

import com.google.common.collect.Sets;
import com.vandelay.industries.searchvolumecomputation.dto.SearchVolumeDto;
import com.vandelay.industries.searchvolumecomputation.exception.SearchVolumeComputationException;
import com.vandelay.industries.searchvolumecomputation.webclient.CompletionApiWebClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchVolumeComputationServiceTest {

    private static final String KEYWORD = "keyword";
    private static final Set<String> SUGGESTED_KEYWORDS = Sets.newHashSet(
            "suggestedKeyword1",
            "suggestedKeyword2"
    );

    @Mock
    private CompletionApiWebClient client;

    @InjectMocks
    private SearchVolumeComputationService service;

    @Test
    public void getSearchVolumeScore_Successful() {
        when(client.requestKeywordSuggestions(KEYWORD)).thenReturn(Flux.fromIterable(SUGGESTED_KEYWORDS));
        SUGGESTED_KEYWORDS.forEach(k -> when(client.requestKeywordSuggestions(k)).thenReturn(Flux.just(k)));

        Mono<SearchVolumeDto> result = service.getSearchVolumeScore(KEYWORD);

        StepVerifier.create(result)
                .expectNext(SearchVolumeDto.builder()
                        .keyword(KEYWORD)
                        .score(SUGGESTED_KEYWORDS.size())
                        .build())
                .verifyComplete();
    }

    @Test
    public void getSearchVolumeScore_ExceptionExpected() {
        when(client.requestKeywordSuggestions(KEYWORD)).thenReturn(Flux.error(new SearchVolumeComputationException()));

        Mono<SearchVolumeDto> result = service.getSearchVolumeScore(KEYWORD);

        StepVerifier.create(result)
                .expectError(SearchVolumeComputationException.class)
                .verify();
    }
}
