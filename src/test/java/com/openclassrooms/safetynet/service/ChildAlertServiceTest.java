package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.record.ChildAlertResponse;
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
class ChildAlertServiceTest {

    @Mock
    private DataService dataService;

    @InjectMocks
    private ChildAlertService childAlertService;

    @BeforeEach
    void setUp() {
        when(dataService.getPersons()).thenReturn(List.of(
            new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "john@email.com"),
            new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenley@email.com")
        ));
    }

    @Test
    void getChildrenByAddress_shouldReturnChildren() {
        when(dataService.getMedicalRecords()).thenReturn(List.of(
            new MedicalRecord("John", "Boyd", "03/06/1984", List.of(), List.of()),
            new MedicalRecord("Tenley", "Boyd", "02/18/2012", List.of(), List.of())
        ));
        List<ChildAlertResponse> result = childAlertService.getChildrenByAddress("1509 Culver St");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).firstName()).isEqualTo("Tenley");
    }

    @Test
    void getChildrenByAddress_shouldReturnHouseholdMembers() {
        when(dataService.getMedicalRecords()).thenReturn(List.of(
            new MedicalRecord("John", "Boyd", "03/06/1984", List.of(), List.of()),
            new MedicalRecord("Tenley", "Boyd", "02/18/2012", List.of(), List.of())
        ));
        List<ChildAlertResponse> result = childAlertService.getChildrenByAddress("1509 Culver St");
        assertThat(result.get(0).householdMembers()).hasSize(1);
        assertThat(result.get(0).householdMembers().get(0).firstName()).isEqualTo("John");
    }

    @Test
    void getChildrenByAddress_shouldReturnEmpty_whenNoChildren() {
        List<ChildAlertResponse> result = childAlertService.getChildrenByAddress("999 Unknown St");
        assertThat(result).isEmpty();
    }
}