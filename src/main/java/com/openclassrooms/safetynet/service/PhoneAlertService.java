package com.openclassrooms.safetynet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhoneAlertService {

    private final DataService dataService;

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