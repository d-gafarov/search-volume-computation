package com.vandelay.industries.searchvolumecomputation.webclient;

import com.google.common.collect.ImmutableMap;
import com.vandelay.industries.searchvolumecomputation.util.CompletionResponseParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Map;

@Component
public class CompletionApiWebClient {
    private static final String KEYWORD_PARAM = "q";
    private static final Map<String, Object> DEFAULT_PARAMS = ImmutableMap.of(
            "search-alias", "aps",
            "client", "amazon-search-ui",
            "mkt", 1
    );

    @Value("${completionAPI.host}")
    private String host;
    @Value("${completionAPI.searchPath}")
    private String searchPath;
    private WebClient client;

    @PostConstruct
    private void initClient() {
        client = WebClient.builder()
                .baseUrl(host)
                .build();
    }

    public Flux<String> requestKeywordSuggestions(String keyword) {
        return client.method(HttpMethod.GET)
                .uri(uriBuilder -> getUri(keyword, uriBuilder))
                .retrieve()
                .bodyToMono(String.class)
                .flatMapMany(CompletionResponseParser::parseResponse);
    }

    private URI getUri(String keyword, UriBuilder uriBuilder) {
        uriBuilder.path(searchPath).queryParam(KEYWORD_PARAM, keyword);
        DEFAULT_PARAMS.forEach(uriBuilder::queryParam);
        return uriBuilder.build();
    }
}
