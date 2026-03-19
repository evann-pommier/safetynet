package com.openclassrooms.safetynet.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.record.FirestationResponse;
import com.openclassrooms.safetynet.record.PersonResponse;
import com.openclassrooms.safetynet.util.AgeCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service gérant la logique métier pour l'endpoint /firestation.
 * Permet de récupérer les personnes couvertes par une caserne
 * et de gérer les mappings adresse/caserne.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FirestationService {

    private final IDataService dataService;

    /**
     * Retourne les personnes couvertes par la caserne donnée,
     * ainsi que le décompte des adultes et des enfants.
     *
     * @param stationNumber le numéro de la caserne
     * @return {@link FirestationResponse} contenant les personnes, le nombre d'adultes et d'enfants
     */
    public FirestationResponse getPersonsByStation(int stationNumber) {
        List<String> addresses = dataService.getFirestations().stream()
                .filter(s -> {
                    try {
                        return Integer.parseInt(s.station()) == stationNumber;
                    } catch (NumberFormatException e) {
                        log.warn("Invalid station number format: {}", s.station());
                        return false;
                    }
                })
                .map(Firestation::address)
                .toList();

        List<Person> coveredPersons = dataService.getPersons().stream()
                .filter(p -> addresses.contains(p.address()))
                .toList();

        List<PersonResponse> persons = coveredPersons.stream()
                .map(p -> new PersonResponse(
                        p.firstName(), p.lastName(), p.address(), p.phone()))
                .toList();

        int adultCount = (int) coveredPersons.stream()
                .filter(p -> AgeCalculator.calculateAge(
                        dataService.getMedicalRecords().stream()
                                .filter(m -> m.firstName().equals(p.firstName()) &&
                                             m.lastName().equals(p.lastName()))
                                .findFirst()
                                .map(m -> m.birthdate())
                                .orElse("01/01/1900")) > 18)
                .count();

        int childCount = coveredPersons.size() - adultCount;

        return new FirestationResponse(persons, adultCount, childCount);
    }

    /**
     * Ajoute un nouveau mapping adresse/caserne.
     * Refuse l'ajout si le mapping existe déjà (même adresse et même station).
     *
     * @param firestation le mapping à ajouter
     * @return true si ajouté, false si le mapping existe déjà
     */
    public boolean addMapping(Firestation firestation) {
        boolean exists = dataService.getFirestations().stream()
            .anyMatch(f -> f.address().equals(firestation.address()) &&
                           f.station().equals(firestation.station()));
        if (exists) {
            log.warn("Mapping already exists: {}", firestation);
            return false;
        }
        dataService.getFirestations().add(firestation);
        log.info("Mapping added: {}", firestation);
        return true;
    }

    /**
     * Met à jour un mapping adresse/caserne existant.
     * Identifie le mapping par la combinaison adresse + numéro de station.
     *
     * @param firestation le mapping avec les nouvelles données
     * @return true si mis à jour, false si le mapping n'existe pas
     */
    public boolean updateMapping(Firestation firestation) {
        boolean removed = dataService.getFirestations().removeIf(f ->
            f.address().equals(firestation.address()) && f.station().equals(firestation.station())
        );
        if (removed) {
            dataService.getFirestations().add(firestation);
            log.info("Mapping updated: {}", firestation);
        } else {
            log.warn("Mapping not found: {}", firestation);
        }
        return removed;
    }

    /**
     * Supprime le mapping correspondant à l'adresse et au numéro de caserne donnés.
     *
     * @param address l'adresse du mapping à supprimer
     * @param station le numéro de caserne du mapping à supprimer
     * @return true si supprimé, false si le mapping n'existe pas
     */
    public boolean deleteMapping(String address, String station) {
        boolean deleted = dataService.getFirestations().removeIf(f ->
            f.address().equals(address) && f.station().equals(station)
        );
        if (deleted) {
            log.info("Mapping deleted for address {} station {}", address, station);
        } else {
            log.warn("Mapping not found for address {} station {}", address, station);
        }
        return deleted;
    }
}