package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.record.FloodResponse;
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
class FloodServiceTest {

    @Mock
    private DataService dataService;

    @InjectMocks
    private FloodService floodService;

    @BeforeEach
    void setUp() {
        when(dataService.getFirestations()).thenReturn(List.of(
            new Firestation("1509 Culver St", "3")
        ));
    }

    @Test
    void getFloodByStations_shouldReturnHouseholds() {
        when(dataService.getPersons()).thenReturn(List.of(
            new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "john@email.com")
        ));
        when(dataService.getMedicalRecords()).thenReturn(List.of(
            new MedicalRecord("John", "Boyd", "03/06/1984", List.of("aznol:350mg"), List.of("nillacilan"))
        ));

        FloodResponse response = floodService.getFloodByStations(List.of(3));
        assertThat(response.households()).containsKey("1509 Culver St");
        assertThat(response.households().get("1509 Culver St")).hasSize(1);
    }

    @Test
    void getFloodByStations_shouldReturnEmpty_whenNoStationMatch() {
        FloodResponse response = floodService.getFloodByStations(List.of(99));
        assertThat(response.households()).isEmpty();
    }

    @Test
    void getFloodByStations_shouldIncludeMedicalInfo() {
        when(dataService.getPersons()).thenReturn(List.of(
            new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "john@email.com")
        ));
        when(dataService.getMedicalRecords()).thenReturn(List.of(
            new MedicalRecord("John", "Boyd", "03/06/1984", List.of("aznol:350mg"), List.of("nillacilan"))
        ));

        FloodResponse response = floodService.getFloodByStations(List.of(3));
        var person = response.households().get("1509 Culver St").get(0);
        assertThat(person.medications()).containsExactly("aznol:350mg");
        assertThat(person.allergies()).containsExactly("nillacilan");
    }
}