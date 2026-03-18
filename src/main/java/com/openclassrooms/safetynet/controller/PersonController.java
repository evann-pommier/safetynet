package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.PersonService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
@Slf4j
public class PersonController {

    private final PersonService personService;

    
    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        log.info("GET /person called");
        return ResponseEntity.ok(personService.getAllPersons());
    }
    @PostMapping
    public ResponseEntity<String> addPerson(@Valid @RequestBody Person person) {
        log.info("POST /person called with: {}", person);
        boolean added = personService.addPerson(person);
        if (!added) return ResponseEntity.status(HttpStatus.CONFLICT).body("Person already exists");
        return ResponseEntity.status(HttpStatus.CREATED).body("Person added");
    }

    @PutMapping
    public ResponseEntity<String> updatePerson(@RequestBody Person person) {
        log.info("PUT /person called with: {}", person);
        boolean updated = personService.updatePerson(person);
        if (!updated) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
        return ResponseEntity.ok("Person updated");
    }

    @DeleteMapping
    public ResponseEntity<String> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        log.info("DELETE /person called for {} {}", firstName, lastName);
        boolean deleted = personService.deletePerson(firstName, lastName);
        if (!deleted) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
        return ResponseEntity.ok("Person deleted");
    }
}