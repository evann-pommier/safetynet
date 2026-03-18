package com.openclassrooms.safetynet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.MedicalRecord;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalRecordService {

    private final DataService dataService;

    public List<MedicalRecord> getAllMedicalRecords() {
        log.debug("Fetching all medical records");
        return dataService.getMedicalRecords();
    }

    public void addMedicalRecord(MedicalRecord record) {
        dataService.getMedicalRecords().add(record);
        log.info("Added medical record for {} {}", record.firstName(), record.lastName());
    }

    public boolean updateMedicalRecord(MedicalRecord record) {
        boolean updated = dataService.getMedicalRecords().removeIf(r ->
            r.firstName().equals(record.firstName()) && r.lastName().equals(record.lastName())
        );
        if (updated) {
            dataService.getMedicalRecords().add(record);
            log.info("Updated medical record for {} {}", record.firstName(), record.lastName());
        } else {
            log.warn("Medical record not found for {} {}", record.firstName(), record.lastName());
        }
        return updated;
    }

    public boolean deleteMedicalRecord(String firstName, String lastName) {
        boolean deleted = dataService.getMedicalRecords().removeIf(r ->
            r.firstName().equals(firstName) && r.lastName().equals(lastName)
        );
        if (deleted) {
            log.info("Deleted medical record for {} {}", firstName, lastName);
        } else {
            log.warn("Medical record not found for {} {}", firstName, lastName);
        }
        return deleted;
    }
}