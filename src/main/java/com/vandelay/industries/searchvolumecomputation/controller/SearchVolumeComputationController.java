package com.vandelay.industries.searchvolumecomputation.controller;

import com.vandelay.industries.searchvolumecomputation.dto.SearchVolumeDto;
import com.vandelay.industries.searchvolumecomputation.service.SearchVolumeComputationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("estimate")
public class SearchVolumeComputationController {

    @Autowired
    private SearchVolumeComputationService service;

    @GetMapping
    public Mono<SearchVolumeDto> getSearchVolumeScore(@RequestParam String keyword) {
        return service.getSearchVolumeScore(keyword);
    }
}
