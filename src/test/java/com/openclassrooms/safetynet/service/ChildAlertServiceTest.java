package com.openclassrooms.safetynet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;

class ChildAlertServiceTest {

    private DataService dataService;
    private ChildAlertService service;

    @BeforeEach
    void setUp() {
        dataService = mock(DataService.class);
        service = new ChildAlertService(dataService);

        when(dataService.getPersons()).thenReturn(List.of(
                new Person("Child", "Boyd", "addr", "city", "zip", "123", "mail"),
                new Person("Adult", "Boyd", "addr", "city", "zip", "123", "mail")
        ));

        when(dataService.getMedicalRecords()).thenReturn(List.of(
                new MedicalRecord("Child", "Boyd", "01/01/2015", List.of(), List.of()),
                new MedicalRecord("Adult", "Boyd", "01/01/1980", List.of(), List.of())
        ));
    }

    @Test
    void shouldReturnOnlyChildren() {
        var result = service.getChildrenByAddress("addr");

        assertEquals(1, result.size());
        assertEquals("Child", result.get(0).firstName());
    }
}