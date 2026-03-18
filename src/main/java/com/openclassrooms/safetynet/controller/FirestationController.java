package com.openclassrooms.safetynet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.record.FirestationResponse;
import com.openclassrooms.safetynet.service.FirestationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/firestation")
public class FirestationController {
	
	private final FirestationService firestationService;
	
	@GetMapping
    public ResponseEntity<FirestationResponse> getPersonsByStation(@RequestParam int stationNumber) {
        log.info("GET /firestation called with stationNumber={}", stationNumber);
        FirestationResponse response = firestationService.getPersonsByStation(stationNumber);
        return ResponseEntity.ok(response);
    }
	
	@PostMapping
    public ResponseEntity<String> addMapping(@RequestBody Firestation firestation) {
        log.info("POST /firestation called with: {}", firestation);
        boolean added = firestationService.addMapping(firestation);
        if (added) {
            log.info("Mapping added successfully: {}", firestation);
            return ResponseEntity.status(HttpStatus.CREATED).body("Mapping added");
        } else {
            log.warn("Mapping already exists: {}", firestation);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Mapping already exists");
        }
    }

    // PUT : mettre à jour le numéro de caserne
	@PutMapping
    public ResponseEntity<String> updateMapping(@RequestBody Firestation firestation) {
        log.info("PUT /firestation called with: {}", firestation);
        boolean updated = firestationService.updateMapping(firestation);
        if (updated) {
            log.info("Mapping updated successfully: {}", firestation);
            return ResponseEntity.ok("Mapping updated");
        } else {
            log.warn("Mapping not found for address: {}", firestation.address());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mapping not found");
        }
    }

    // DELETE : supprimer un mapping
	@DeleteMapping
	public ResponseEntity<String> deleteMapping(@RequestParam String address, @RequestParam String station) {
	    log.info("DELETE /firestation called for address: {} station: {}", address, station);
	    boolean deleted = firestationService.deleteMapping(address, station);
	    if (deleted) return ResponseEntity.ok("Mapping deleted");
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mapping not found");
	}

}
