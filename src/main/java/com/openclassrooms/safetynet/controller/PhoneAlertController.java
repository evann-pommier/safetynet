package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.service.PhoneAlertService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/phoneAlert")
@RequiredArgsConstructor
@Slf4j
public class PhoneAlertController {

    private final PhoneAlertService service;

    @GetMapping
    public List<String> getPhones(@RequestParam int firestation) {
        log.info("Request phoneAlert for station {}", firestation);
        return service.getPhonesByStation(firestation);
    }
}