package com.openclassrooms.safetynet.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.record.FireResponse;
import com.openclassrooms.safetynet.record.FloodResponse;
import com.openclassrooms.safetynet.util.AgeCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service gérant la logique métier pour l'endpoint /flood.
 * Permet de récupérer les foyers desservis par une ou plusieurs casernes,
 * regroupés par adresse avec les informations médicales de chaque habitant.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FloodService {

    private final IDataService dataService;

    /**
     * Retourne tous les foyers desservis par les casernes données,
     * regroupés par adresse. Chaque habitant est accompagné de son âge,
     * ses médicaments, ses allergies et les casernes desservant son adresse.
     *
     * @param stationNumbers la liste des numéros de casernes
     * @return {@link FloodResponse} contenant une map adresse -> liste des habitants
     */
    public FloodResponse getFloodByStations(List<Integer> stationNumbers) {
        log.debug("Fetching households for stations: {}", stationNumbers);

        List<String> addresses = dataService.getFirestations().stream()
                .filter(f -> {
                    try {
                        return stationNumbers.contains(Integer.parseInt(f.station()));
                    } catch (NumberFormatException e) {
                        log.warn("Invalid station number format: {}", f.station());
                        return false;
                    }
                })
                .map(Firestation::address)
                .toList();

        Map<String, List<FireResponse>> households = new HashMap<>();

        for (String address : addresses) {
            List<Person> residents = dataService.getPersons().stream()
                    .filter(p -> p.address().equals(address))
                    .toList();

            List<FireResponse> responses = residents.stream().map(p -> {
                MedicalRecord record = dataService.getMedicalRecords().stream()
                        .filter(m -> m.firstName().equals(p.firstName()) && m.lastName().equals(p.lastName()))
                        .findFirst()
                        .orElse(null);

                int age = record != null ? AgeCalculator.calculateAge(record.birthdate()) : 0;
                List<String> medications = record != null ? record.medications() : Collections.emptyList();
                List<String> allergies = record != null ? record.allergies() : Collections.emptyList();

                List<String> stations = dataService.getFirestations().stream()
                        .filter(f -> f.address().equals(address))
                        .map(Firestation::station)
                        .toList();

                return new FireResponse(
                        p.firstName(),
                        p.lastName(),
                        p.phone(),
                        age,
                        medications,
                        allergies,
                        stations
                );
            }).toList();

            households.put(address, responses);
        }

        return new FloodResponse(households);
    }
}