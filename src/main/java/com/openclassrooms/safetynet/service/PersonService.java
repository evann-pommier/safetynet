package com.openclassrooms.safetynet.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.openclassrooms.safetynet.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service gérant la logique métier pour l'endpoint /person.
 * Permet de consulter et de gérer les personnes
 * (ajout, mise à jour, suppression).
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private final IDataService dataService;

    /**
     * Retourne la liste de toutes les personnes.
     *
     * @return liste de {@link Person}
     */
    public List<Person> getAllPersons() {
        return dataService.getPersons();
    }

    /**
     * Vérifie si une personne existe déjà dans le système.
     *
     * @param firstName le prénom de la personne
     * @param lastName  le nom de la personne
     * @return true si la personne existe, false sinon
     */
    public boolean personExists(String firstName, String lastName) {
        return dataService.getPersons().stream()
            .anyMatch(p -> p.firstName().equals(firstName) && p.lastName().equals(lastName));
    }

    /**
     * Ajoute une nouvelle personne.
     * Le prénom et le nom forment un identifiant unique.
     *
     * @param person la personne à ajouter
     * @return true si ajoutée, false si elle existe déjà
     */
    public boolean addPerson(Person person) {
        if (personExists(person.firstName(), person.lastName())) {
            log.warn("Person already exists: {} {}", person.firstName(), person.lastName());
            return false;
        }
        dataService.getPersons().add(person);
        log.info("Person added: {} {}", person.firstName(), person.lastName());
        return true;
    }

    /**
     * Met à jour une personne existante.
     * Le prénom et le nom servent d'identifiant unique et ne peuvent pas être modifiés.
     *
     * @param person la personne avec les nouvelles données
     * @return true si mise à jour, false si la personne n'existe pas
     */
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

    /**
     * Supprime la personne correspondant au prénom et nom donnés.
     *
     * @param firstName le prénom de la personne
     * @param lastName  le nom de la personne
     * @return true si supprimée, false si la personne n'existe pas
     */
    public boolean deletePerson(String firstName, String lastName) {
        boolean deleted = dataService.getPersons().removeIf(
            p -> p.firstName().equals(firstName) && p.lastName().equals(lastName)
        );
        if (deleted) log.info("Person deleted: {} {}", firstName, lastName);
        else log.warn("Person not found: {} {}", firstName, lastName);
        return deleted;
    }
}