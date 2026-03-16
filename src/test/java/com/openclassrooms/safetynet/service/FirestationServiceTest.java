package com.openclassrooms.safetynet.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.record.FirestationResponse;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FirestationServiceTest {

    private DataService dataService;  
    private FirestationService firestationService;

    @BeforeEach
    void setUp() {
        dataService = mock(DataService.class); // Mockito.mock
        firestationService = new FirestationService(dataService);

        // Mock du DataService
        List<Firestation> firestations = new ArrayList<>();
        firestations.add(new Firestation("1509 Culver St", "3"));
        when(dataService.getFirestations()).thenReturn(firestations);

        List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        when(dataService.getPersons()).thenReturn(persons);

        List<MedicalRecord> medicalRecords = new ArrayList<>();
        medicalRecords.add(new MedicalRecord("John", "Boyd", "03/06/1984", new ArrayList<>(), new ArrayList<>()));
        when(dataService.getMedicalRecords()).thenReturn(medicalRecords);
    }

    @Test
    void testGetPersonsByStation() {
        FirestationResponse response = firestationService.getPersonsByStation(3);

        assertNotNull(response);
        assertEquals(1, response.persons().size());
        assertEquals("John", response.persons().get(0).firstName());
        assertEquals(1, response.adultCount());
        assertEquals(0, response.childCount());
    }
}