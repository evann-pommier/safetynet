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

@Service
@RequiredArgsConstructor
@Slf4j
public class FloodService {

    private final DataService dataService;

    public FloodResponse getFloodByStations(List<Integer> stationNumbers) {
        log.debug("Fetching households for stations: {}", stationNumbers);

        // récupérer toutes les adresses couvertes par ces stations
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

        // pour chaque adresse, lister les personnes avec leurs infos
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

                // trouver toutes les stations qui desservent l'adresse
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