package com.openclassrooms.safetynet.service;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service gérant la logique métier pour l'endpoint /phoneAlert.
 * Permet de récupérer les numéros de téléphone des habitants
 * desservis par une caserne de pompiers donnée.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PhoneAlertService {

    private final IDataService dataService;

    /**
     * Retourne la liste des numéros de téléphone des résidents
     * couverts par la caserne donnée, sans doublons.
     *
     * @param stationNumber le numéro de la caserne
     * @return liste des numéros de téléphone distincts
     */
    public List<String> getPhonesByStation(int stationNumber) {
        log.info("Fetching phones for station {}", stationNumber);

        List<String> addresses = dataService.getFirestations().stream()
                .filter(f -> {
                    try {
                        return Integer.parseInt(f.station()) == stationNumber;
                    } catch (NumberFormatException e) {
                        log.warn("Invalid station number format: {}", f.station());
                        return false;
                    }
                })
                .map(f -> f.address())
                .toList();

        List<String> phones = dataService.getPersons().stream()
                .filter(p -> addresses.contains(p.address()))
                .map(p -> p.phone())
                .distinct()
                .toList();

        log.info("Found {} phones for station {}", phones.size(), stationNumber);
        return phones;
    }
}