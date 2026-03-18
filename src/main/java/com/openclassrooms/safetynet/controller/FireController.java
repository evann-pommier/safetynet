package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.record.FireResponse;
import com.openclassrooms.safetynet.service.FireService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/fire")
@RequiredArgsConstructor
@Slf4j
public class FireController {

    private final FireService service;

    @GetMapping
    public List<FireResponse> getFireInfo(@RequestParam String address) {
        log.info("Request fire info for {}", address);
        return service.getFireInfoByAddress(address);
    }
}