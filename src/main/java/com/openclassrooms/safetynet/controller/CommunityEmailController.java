package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.service.CommunityEmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/communityEmail")
@RequiredArgsConstructor
@Slf4j
public class CommunityEmailController {

    private final CommunityEmailService service;

    @GetMapping
    public List<String> getEmails(@RequestParam String city) {
        log.info("Requête emails pour la ville {}", city);
        return service.getEmailsByCity(city);
    }
}