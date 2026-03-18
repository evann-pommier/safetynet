package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.Person;
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
class PhoneAlertServiceTest {

    @Mock
    private DataService dataService;

    @InjectMocks
    private PhoneAlertService phoneAlertService;

    @BeforeEach
    void setUp() {
        when(dataService.getFirestations()).thenReturn(List.of(
            new Firestation("1509 Culver St", "3")
        ));
        when(dataService.getPersons()).thenReturn(List.of(
            new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "john@email.com"),
            new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jacob@email.com")
        ));
    }

    @Test
    void getPhonesByStation_shouldReturnPhones() {
        List<String> phones = phoneAlertService.getPhonesByStation(3);
        assertThat(phones).hasSize(1);
        assertThat(phones).containsExactly("841-874-6512");
    }

    @Test
    void getPhonesByStation_shouldReturnEmpty_whenNoStationMatch() {
        List<String> phones = phoneAlertService.getPhonesByStation(99);
        assertThat(phones).isEmpty();
    }
}