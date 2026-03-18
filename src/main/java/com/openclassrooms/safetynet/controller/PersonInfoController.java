package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.service.PersonInfoService;
import com.openclassrooms.safetynet.record.PersonInfoResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/personInfo")
@RequiredArgsConstructor
@Slf4j
public class PersonInfoController {

    private final PersonInfoService service;

    @GetMapping
    public List<PersonInfoResponse> getInfo(@RequestParam String lastName) {
        log.info("Request person info for lastName={}", lastName);
        return service.getByLastName(lastName);
    }
}