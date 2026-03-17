package com.openclassrooms.safetynet.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.record.FirestationResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FirestationServiceTest {

    private DataService dataService;
    private FirestationService service;

    @BeforeEach
    void setUp() {
        dataService = mock(DataService.class);
        service = new FirestationService(dataService);

        when(dataService.getFirestations()).thenReturn(List.of(
                new Firestation("1509 Culver St", "3")
        ));

        when(dataService.getPersons()).thenReturn(List.of(
                new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "123", "mail")
        ));

        when(dataService.getMedicalRecords()).thenReturn(List.of(
                new MedicalRecord("John", "Boyd", "03/06/1984", List.of(), List.of())
        ));
    }

    @Test
    void shouldReturnPersonsByStation() {
        FirestationResponse result = service.getPersonsByStation(3);

        assertEquals(1, result.persons().size());
        assertEquals("John", result.persons().get(0).firstName());
    }
}