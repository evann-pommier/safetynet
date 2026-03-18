package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.record.ChildAlertResponse;
import com.openclassrooms.safetynet.service.ChildAlertService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/childAlert")
@RequiredArgsConstructor
@Slf4j
public class ChildAlertController {

    private final ChildAlertService childAlertService;

    @GetMapping
    public List<ChildAlertResponse> getChildrenByAddress(@RequestParam String address) {

        log.info("Request received for /childAlert with address: {}", address);

        List<ChildAlertResponse> response = childAlertService.getChildrenByAddress(address);

        log.info("Response returned: {} children found", response.size());

        return response;
    }
}