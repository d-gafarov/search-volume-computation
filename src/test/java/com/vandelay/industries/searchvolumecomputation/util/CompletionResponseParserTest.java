package com.vandelay.industries.searchvolumecomputation.util;

import com.vandelay.industries.searchvolumecomputation.exception.SearchVolumeComputationException;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class CompletionResponseParserTest {

    private static final String TEST_RESPONSE = "[\"iphone\",[\"iphone 13 pro max case\",\"iphone 13 pro case\"],[{},{},{},{},{},{},{},{},{},{}],[],\"3LD3K4AKBWG1H\"]";
    private static final String[] EXPECTED = {
            "iphone 13 pro max case",
            "iphone 13 pro case"
    };

    @Test
    public void parseResponse_Successful() {
        Flux<String> parsedKeywords = CompletionResponseParser.parseResponse(TEST_RESPONSE);

        StepVerifier.create(parsedKeywords)
                .expectNext(EXPECTED)
                .verifyComplete();
    }

    @Test
    public void parseResponse_Failed() {
        Flux<String> parsedKeywords = CompletionResponseParser.parseResponse("[\"iphone\",]");

        StepVerifier.create(parsedKeywords)
                .expectError(SearchVolumeComputationException.class)
                .verify();
    }
}
