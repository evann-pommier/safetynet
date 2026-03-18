package com.openclassrooms.safetynet.service;

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
class CommunityEmailServiceTest {

    @Mock
    private DataService dataService;

    @InjectMocks
    private CommunityEmailService communityEmailService;

    @BeforeEach
    void setUp() {
        when(dataService.getPersons()).thenReturn(List.of(
            new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "john@email.com"),
            new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513", "john@email.com"),
            new Person("Other", "Person", "123 St", "Paris", "75000", "000-000-0000", "other@email.com")
        ));
    }

    @Test
    void getEmailsByCity_shouldReturnEmails() {
        List<String> emails = communityEmailService.getEmailsByCity("Culver");
        assertThat(emails).hasSize(1);
        assertThat(emails).containsExactly("john@email.com");
    }

    @Test
    void getEmailsByCity_shouldBeCaseInsensitive() {
        List<String> emails = communityEmailService.getEmailsByCity("culver");
        assertThat(emails).hasSize(1);
    }

    @Test
    void getEmailsByCity_shouldReturnEmpty_whenCityNotFound() {
        List<String> emails = communityEmailService.getEmailsByCity("Unknown");
        assertThat(emails).isEmpty();
    }
}