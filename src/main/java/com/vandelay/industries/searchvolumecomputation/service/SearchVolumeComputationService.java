package com.vandelay.industries.searchvolumecomputation.service;

import com.vandelay.industries.searchvolumecomputation.dto.SearchVolumeDto;
import com.vandelay.industries.searchvolumecomputation.webclient.CompletionApiWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchVolumeComputationService {

    @Autowired
    private CompletionApiWebClient completionApiWebClient;

    public Mono<SearchVolumeDto> getSearchVolumeScore(String keyword) {
        return computeScore(keyword)
                .map(score -> SearchVolumeDto.builder()
                        .keyword(keyword)
                        .score(score)
                        .build());
    }

    private Mono<Integer> computeScore(String keyword) {
        return completionApiWebClient.requestKeywordSuggestions(keyword)
                .parallel(10)
                .map(completionApiWebClient::requestKeywordSuggestions)
                .flatMap(keywordsFlux -> keywordsFlux.collect(Collectors.toList()).map(List::size))
                .sequential()
                .reduce(0, Integer::sum);
    }
}
