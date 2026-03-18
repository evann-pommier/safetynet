package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.record.FloodResponse;
import com.openclassrooms.safetynet.service.FloodService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/flood")
@RequiredArgsConstructor
@Slf4j
public class FloodController {

    private final FloodService floodService;

    @GetMapping("/stations")
    public FloodResponse getFlood(@RequestParam List<Integer> stations) {
        log.info("Request flood for stations {}", stations);
        return floodService.getFloodByStations(stations);
    }
}