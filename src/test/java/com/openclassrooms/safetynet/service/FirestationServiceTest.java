package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.record.FirestationResponse;
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
class FirestationServiceTest {

    @Mock
    private DataService dataService;

    @InjectMocks
    private FirestationService firestationService;

    private List<Firestation> firestations;

    @BeforeEach
    void setUp() {
        firestations = new ArrayList<>(List.of(new Firestation("1509 Culver St", "3")));
        when(dataService.getFirestations()).thenReturn(firestations);
    }

    @Test
    void getPersonsByStation_shouldReturnPersonsAndCounts() {
        when(dataService.getPersons()).thenReturn(List.of(
            new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "john@email.com"),
            new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenley@email.com")
        ));
        when(dataService.getMedicalRecords()).thenReturn(List.of(
            new MedicalRecord("John", "Boyd", "03/06/1984", List.of(), List.of()),
            new MedicalRecord("Tenley", "Boyd", "02/18/2012", List.of(), List.of())
        ));

        FirestationResponse response = firestationService.getPersonsByStation(3);
        assertThat(response.persons()).hasSize(2);
        assertThat(response.adultCount()).isEqualTo(1);
        assertThat(response.childCount()).isEqualTo(1);
    }

    @Test
    void getPersonsByStation_shouldReturnEmpty_whenNoStationMatch() {
        FirestationResponse response = firestationService.getPersonsByStation(99);
        assertThat(response.persons()).isEmpty();
    }

    @Test
    void addMapping_shouldReturnTrue_whenMappingDoesNotExist() {
        Firestation newMapping = new Firestation("999 New St", "1");
        assertThat(firestationService.addMapping(newMapping)).isTrue();
        assertThat(firestations).hasSize(2);
    }

    @Test
    void addMapping_shouldReturnFalse_whenMappingAlreadyExists() {
        assertThat(firestationService.addMapping(new Firestation("1509 Culver St", "3"))).isFalse();
    }

    @Test
    void updateMapping_shouldReturnTrue_whenMappingExists() {
        assertThat(firestationService.updateMapping(new Firestation("1509 Culver St", "3"))).isTrue();
    }

    @Test
    void updateMapping_shouldReturnFalse_whenMappingDoesNotExist() {
        assertThat(firestationService.updateMapping(new Firestation("Unknown St", "9"))).isFalse();
    }

    @Test
    void deleteMapping_shouldReturnTrue_whenMappingExists() {
        assertThat(firestationService.deleteMapping("1509 Culver St", "3")).isTrue();
        assertThat(firestations).isEmpty();
    }

    @Test
    void deleteMapping_shouldReturnFalse_whenMappingDoesNotExist() {
        assertThat(firestationService.deleteMapping("Unknown St", "9")).isFalse();
    }
}