package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.record.PersonInfoResponse;
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
class PersonInfoServiceTest {

    @Mock
    private DataService dataService;

    @InjectMocks
    private PersonInfoService personInfoService;

    @BeforeEach
    void setUp() {
        when(dataService.getPersons()).thenReturn(List.of(
            new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "john@email.com"),
            new Person("Other", "Person", "123 St", "Culver", "97451", "000", "other@email.com")
        ));
    }

    @Test
    void getByLastName_shouldReturnPersonInfo() {
        when(dataService.getMedicalRecords()).thenReturn(List.of(
            new MedicalRecord("John", "Boyd", "03/06/1984", List.of("aznol:350mg"), List.of("nillacilan"))
        ));
        List<PersonInfoResponse> result = personInfoService.getByLastName("Boyd");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).firstName()).isEqualTo("John");
        assertThat(result.get(0).medications()).containsExactly("aznol:350mg");
    }

    @Test
    void getByLastName_shouldBeCaseInsensitive() {
        when(dataService.getMedicalRecords()).thenReturn(List.of(
            new MedicalRecord("John", "Boyd", "03/06/1984", List.of(), List.of())
        ));
        List<PersonInfoResponse> result = personInfoService.getByLastName("boyd");
        assertThat(result).hasSize(1);
    }

    @Test
    void getByLastName_shouldReturnEmpty_whenNoMatch() {
        List<PersonInfoResponse> result = personInfoService.getByLastName("Unknown");
        assertThat(result).isEmpty();
    }

    @Test
    void getByLastName_shouldHandleMissingMedicalRecord() {
        when(dataService.getMedicalRecords()).thenReturn(List.of());
        List<PersonInfoResponse> result = personInfoService.getByLastName("Person");
        assertThat(result.get(0).age()).isEqualTo(0);
        assertThat(result.get(0).medications()).isEmpty();
    }
}