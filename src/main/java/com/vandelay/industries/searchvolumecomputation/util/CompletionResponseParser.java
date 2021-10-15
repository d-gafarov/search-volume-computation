package com.vandelay.industries.searchvolumecomputation.util;

import com.vandelay.industries.searchvolumecomputation.exception.SearchVolumeComputationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompletionResponseParser {
    private static final Pattern RESPONSE_PATTERN = Pattern.compile("\\[.+\",\\[(.+)],\\[\\{.+\n");

    public static Flux<String> parseResponse(String response) {
        Matcher responseMatcher = RESPONSE_PATTERN.matcher(response);
        if (responseMatcher.matches()) {
            String group = responseMatcher.group(1);
            String[] keywords = group.replace("\"", "").split(",");
            return Flux.fromArray(keywords);
        } else {
            return Flux.error(new SearchVolumeComputationException("Unexpected response pattern"));
        }
    }
}
