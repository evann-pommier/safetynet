package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.record.FireResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FireServiceTest {

    @Mock
    private DataService dataService;

    @InjectMocks
    private FireService fireService;

    @BeforeEach
    void setUp() {
        when(dataService.getFirestations()).thenReturn(List.of(
            new Firestation("1509 Culver St", "3")
        ));
        when(dataService.getPersons()).thenReturn(List.of(
            new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "john@email.com")
        ));
    }

    @Test
    void getFireInfoByAddress_shouldReturnResidents() {
        when(dataService.getMedicalRecords()).thenReturn(List.of(
            new MedicalRecord("John", "Boyd", "03/06/1984", List.of("aznol:350mg"), List.of("nillacilan"))
        ));
        List<FireResponse> result = fireService.getFireInfoByAddress("1509 Culver St");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).firstName()).isEqualTo("John");
        assertThat(result.get(0).stations()).containsExactly("3");
    }

    @Test
    void getFireInfoByAddress_shouldReturnMedicalInfo() {
        when(dataService.getMedicalRecords()).thenReturn(List.of(
            new MedicalRecord("John", "Boyd", "03/06/1984", List.of("aznol:350mg"), List.of("nillacilan"))
        ));
        List<FireResponse> result = fireService.getFireInfoByAddress("1509 Culver St");
        assertThat(result.get(0).medications()).containsExactly("aznol:350mg");
        assertThat(result.get(0).allergies()).containsExactly("nillacilan");
    }

    @Test
    void getFireInfoByAddress_shouldReturnEmpty_whenNoResidents() {
        List<FireResponse> result = fireService.getFireInfoByAddress("Unknown St");
        assertThat(result).isEmpty();
    }

    @Test
    void getFireInfoByAddress_shouldHandleMissingMedicalRecord() {
        when(dataService.getMedicalRecords()).thenReturn(List.of());
        List<FireResponse> result = fireService.getFireInfoByAddress("1509 Culver St");
        assertThat(result.get(0).age()).isEqualTo(0);
        assertThat(result.get(0).medications()).isEmpty();
    }
}