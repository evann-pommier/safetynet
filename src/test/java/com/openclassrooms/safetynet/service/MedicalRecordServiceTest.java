package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceTest {

    @Mock
    private DataService dataService;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    private List<MedicalRecord> records;
    private MedicalRecord john;

    @BeforeEach
    void setUp() {
        john = new MedicalRecord("John", "Boyd", "03/06/1984", List.of("aznol:350mg"), List.of("nillacilan"));
        records = new ArrayList<>(List.of(john));
        when(dataService.getMedicalRecords()).thenReturn(records);
    }

    @Test
    void getAllMedicalRecords_shouldReturnAll() {
        assertThat(medicalRecordService.getAllMedicalRecords()).hasSize(1);
    }

    @Test
    void addMedicalRecord_shouldAddRecord() {
        MedicalRecord newRecord = new MedicalRecord("Jane", "Doe", "01/01/2000", List.of(), List.of());
        medicalRecordService.addMedicalRecord(newRecord);
        assertThat(records).hasSize(2);
    }

    @Test
    void updateMedicalRecord_shouldReturnTrue_whenRecordExists() {
        MedicalRecord updated = new MedicalRecord("John", "Boyd", "03/06/1984", List.of("newMed:100mg"), List.of());
        assertThat(medicalRecordService.updateMedicalRecord(updated)).isTrue();
        assertThat(records.get(0).medications()).containsExactly("newMed:100mg");
    }

    @Test
    void updateMedicalRecord_shouldReturnFalse_whenRecordDoesNotExist() {
        MedicalRecord unknown = new MedicalRecord("Unknown", "Person", "01/01/2000", List.of(), List.of());
        assertThat(medicalRecordService.updateMedicalRecord(unknown)).isFalse();
    }

    @Test
    void deleteMedicalRecord_shouldReturnTrue_whenRecordExists() {
        assertThat(medicalRecordService.deleteMedicalRecord("John", "Boyd")).isTrue();
        assertThat(records).isEmpty();
    }

    @Test
    void deleteMedicalRecord_shouldReturnFalse_whenRecordDoesNotExist() {
        assertThat(medicalRecordService.deleteMedicalRecord("Unknown", "Person")).isFalse();
    }
}