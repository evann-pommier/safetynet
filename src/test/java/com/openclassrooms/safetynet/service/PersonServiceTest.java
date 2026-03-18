package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.Person;
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
class PersonServiceTest {

    @Mock
    private DataService dataService;

    @InjectMocks
    private PersonService personService;

    private List<Person> persons;
    private Person john;

    @BeforeEach
    void setUp() {
        john = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "john@email.com");
        persons = new ArrayList<>(List.of(john));
        when(dataService.getPersons()).thenReturn(persons);
    }

    @Test
    void getAllPersons_shouldReturnAllPersons() {
        assertThat(personService.getAllPersons()).hasSize(1);
    }

    @Test
    void addPerson_shouldReturnTrue_whenPersonDoesNotExist() {
        Person newPerson = new Person("Jane", "Doe", "123 Main St", "Culver", "97451", "123-456-7890", "jane@email.com");
        assertThat(personService.addPerson(newPerson)).isTrue();
        assertThat(persons).hasSize(2);
    }

    @Test
    void addPerson_shouldReturnFalse_whenPersonAlreadyExists() {
        assertThat(personService.addPerson(john)).isFalse();
        assertThat(persons).hasSize(1);
    }

    @Test
    void updatePerson_shouldReturnTrue_whenPersonExists() {
        Person updated = new Person("John", "Boyd", "999 New St", "Culver", "97451", "000-000-0000", "new@email.com");
        assertThat(personService.updatePerson(updated)).isTrue();
        assertThat(persons.get(0).address()).isEqualTo("999 New St");
    }

    @Test
    void updatePerson_shouldReturnFalse_whenPersonDoesNotExist() {
        Person unknown = new Person("Unknown", "Person", "123 St", "Culver", "97451", "000", "u@email.com");
        assertThat(personService.updatePerson(unknown)).isFalse();
    }

    @Test
    void deletePerson_shouldReturnTrue_whenPersonExists() {
        assertThat(personService.deletePerson("John", "Boyd")).isTrue();
        assertThat(persons).isEmpty();
    }

    @Test
    void deletePerson_shouldReturnFalse_whenPersonDoesNotExist() {
        assertThat(personService.deletePerson("Unknown", "Person")).isFalse();
    }

    @Test
    void personExists_shouldReturnTrue_whenPersonExists() {
        assertThat(personService.personExists("John", "Boyd")).isTrue();
    }

    @Test
    void personExists_shouldReturnFalse_whenPersonDoesNotExist() {
        assertThat(personService.personExists("Unknown", "Person")).isFalse();
    }
}