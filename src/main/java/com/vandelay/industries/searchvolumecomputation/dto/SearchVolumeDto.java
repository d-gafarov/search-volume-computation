package com.vandelay.industries.searchvolumecomputation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchVolumeDto {
    private String keyword;
    private int score;
}
