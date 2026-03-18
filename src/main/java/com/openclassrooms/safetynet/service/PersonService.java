package com.openclassrooms.safetynet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.Person;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private final DataService dataService;

    public List<Person> getAllPersons() {
        return dataService.getPersons();
    }

    public boolean personExists(String firstName, String lastName) {
        return dataService.getPersons().stream()
            .anyMatch(p -> p.firstName().equals(firstName) && p.lastName().equals(lastName));
    }

    public boolean addPerson(Person person) {
        if (personExists(person.firstName(), person.lastName())) {
            log.warn("Person already exists: {} {}", person.firstName(), person.lastName());
            return false;
        }
        dataService.getPersons().add(person);
        log.info("Person added: {} {}", person.firstName(), person.lastName());
        return true;
    }

    public boolean updatePerson(Person person) {
        Optional<Person> existing = dataService.getPersons().stream()
            .filter(p -> p.firstName().equals(person.firstName()) && p.lastName().equals(person.lastName()))
            .findFirst();
        if (existing.isEmpty()) {
            log.warn("Person not found: {} {}", person.firstName(), person.lastName());
            return false;
        }
        dataService.getPersons().remove(existing.get());
        dataService.getPersons().add(person);
        log.info("Person updated: {} {}", person.firstName(), person.lastName());
        return true;
    }

    public boolean deletePerson(String firstName, String lastName) {
        boolean deleted = dataService.getPersons().removeIf(
            p -> p.firstName().equals(firstName) && p.lastName().equals(lastName)
        );
        if (deleted) log.info("Person deleted: {} {}", firstName, lastName);
        else log.warn("Person not found: {} {}", firstName, lastName);
        return deleted;
    }
}