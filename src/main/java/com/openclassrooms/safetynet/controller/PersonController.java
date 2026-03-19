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

/**
 * Contrôleur REST pour l'endpoint /person.
 * Permet de consulter et de gérer les personnes
 * (ajout, mise à jour, suppression).
 */
@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
@Slf4j
public class PersonController {

    private final PersonService personService;

    /**
     * Retourne la liste de toutes les personnes.
     *
     * @return liste de {@link Person}
     */
    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        log.info("GET /person called");
        log.debug("Delegating to personService.getAllPersons()");
        List<Person> persons = personService.getAllPersons();
        log.debug("personService returned {} persons", persons.size());
        log.info("Response returned: {} persons found", persons.size());
        return ResponseEntity.ok(persons);
    }

    /**
     * Ajoute une nouvelle personne.
     * Le prénom et le nom forment un identifiant unique.
     *
     * @param person la personne à ajouter
     * @return 201 si ajoutée, 409 si elle existe déjà
     */
    @PostMapping
    public ResponseEntity<String> addPerson(@Valid @RequestBody Person person) {
        log.info("POST /person called with: {}", person);
        log.debug("Delegating to personService.addPerson({})", person);
        boolean added = personService.addPerson(person);
        if (!added) {
            log.warn("Person already exists: {} {}", person.firstName(), person.lastName());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Person already exists");
        }
        log.info("Person added successfully: {} {}", person.firstName(), person.lastName());
        return ResponseEntity.status(HttpStatus.CREATED).body("Person added");
    }

    /**
     * Met à jour une personne existante.
     * Le prénom et le nom servent d'identifiant unique et ne peuvent pas être modifiés.
     *
     * @param person la personne avec les nouvelles données
     * @return 200 si mise à jour, 404 si la personne n'existe pas
     */
    @PutMapping
    public ResponseEntity<String> updatePerson(@Valid @RequestBody Person person) {
        log.info("PUT /person called with: {}", person);
        log.debug("Delegating to personService.updatePerson({})", person);
        boolean updated = personService.updatePerson(person);
        if (!updated) {
            log.warn("Person not found: {} {}", person.firstName(), person.lastName());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
        }
        log.info("Person updated successfully: {} {}", person.firstName(), person.lastName());
        return ResponseEntity.ok("Person updated");
    }

    /**
     * Supprime la personne correspondant au prénom et nom donnés.
     *
     * @param firstName le prénom de la personne
     * @param lastName  le nom de la personne
     * @return 200 si supprimée, 404 si la personne n'existe pas
     */
    @DeleteMapping
    public ResponseEntity<String> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        log.info("DELETE /person called for {} {}", firstName, lastName);
        log.debug("Delegating to personService.deletePerson({}, {})", firstName, lastName);
        boolean deleted = personService.deletePerson(firstName, lastName);
        if (!deleted) {
            log.warn("Person not found: {} {}", firstName, lastName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
        }
        log.info("Person deleted successfully: {} {}", firstName, lastName);
        return ResponseEntity.ok("Person deleted");
    }
}