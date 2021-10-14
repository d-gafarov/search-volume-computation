package com.vandelay.industries.searchvolumecomputation.util;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

public class CompletionResponseParserTest {

    private static final String TEST_RESPONSE = "[\"iphone\",[\"iphone 13 pro max case\",\"iphone 13 pro case\"],[{},{},{},{},{},{},{},{},{},{}],[],\"3LD3K4AKBWG1H\"]";
    private static final Set<String> EXPECTED = Sets.newHashSet(
            "iphone 13 pro max case",
            "iphone 13 pro case"
    );

    @Test
    public void parseResponse_Successful() {
        Set<String> parsedKeywords = CompletionResponseParser.parseResponse(TEST_RESPONSE)
                .toStream()
                .collect(Collectors.toSet());
        Assertions.assertEquals(EXPECTED, parsedKeywords);
    }
}
