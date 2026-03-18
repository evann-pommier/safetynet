package com.openclassrooms.safetynet.controller;

import java.util.List;

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

import com.openclassrooms.safetynet.service.MedicalRecordService;
import com.openclassrooms.safetynet.model.MedicalRecord;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/medicalRecord")
@RequiredArgsConstructor
@Slf4j
public class MedicalRecordController {

    private final MedicalRecordService service;

    @GetMapping
    public List<MedicalRecord> getAll() {
        log.info("GET /medicalRecord called");
        return service.getAllMedicalRecords();
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody MedicalRecord record) {
        log.info("POST /medicalRecord called for {} {}", record.firstName(), record.lastName());
        service.addMedicalRecord(record);
        return ResponseEntity.status(HttpStatus.CREATED).body("Medical record added");
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody MedicalRecord record) {
        log.info("PUT /medicalRecord called for {} {}", record.firstName(), record.lastName());
        boolean updated = service.updateMedicalRecord(record);
        if (!updated) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medical record not found");
        return ResponseEntity.ok("Medical record updated");
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam String firstName, @RequestParam String lastName) {
        log.info("DELETE /medicalRecord called for {} {}", firstName, lastName);
        boolean deleted = service.deleteMedicalRecord(firstName, lastName);
        if (!deleted) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medical record not found");
        return ResponseEntity.ok("Medical record deleted");
    }
}