package com.openclassrooms.safetynet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.record.FirestationResponse;
import com.openclassrooms.safetynet.service.FirestationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/firestation")
public class FirestationController {
	
	private final FirestationService firestationService;
	
	
	public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }
	@GetMapping
    public FirestationResponse getPersonsByStation(@RequestParam int stationNumber){

        log.info("GET /firestation stationNumber={}", stationNumber);

        return firestationService.getPersonsByStation(stationNumber);
    }

}
