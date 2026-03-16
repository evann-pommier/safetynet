package com.openclassrooms.safetynet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.record.FirestationResponse;
import com.openclassrooms.safetynet.record.PersonResponse;
import com.openclassrooms.safetynet.util.AgeCalculator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirestationService {

    private final DataService dataService;

    public FirestationResponse getPersonsByStation(int stationNumber) {
        log.debug("Getting persons for station {}", stationNumber);

        List<String> addresses = dataService.getFirestations().stream()
                .filter(s -> Integer.parseInt(s.getStation()) == stationNumber)
                .map(Firestation::getAddress)
                .toList();

        List<Person> coveredPersons = dataService.getPersons().stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .toList();

        List<PersonResponse> persons = coveredPersons.stream()
                .map(p -> new PersonResponse(p.getFirstName(), p.getLastName(), p.getAddress(), p.getPhone()))
                .collect(Collectors.toList());

        int adultCount = (int) coveredPersons.stream()
                .filter(p -> {
                    int age = AgeCalculator.calculateAge(
                            dataService.getMedicalRecords().stream()
                                    .filter(m -> m.getFirstName().equals(p.getFirstName()) &&
                                                 m.getLastName().equals(p.getLastName()))
                                    .findFirst()
                                    .map(m -> m.getBirthdate())
                                    .orElse("01/01/1900")
                    );
                    return age > 18;
                }).count();

        int childCount = coveredPersons.size() - adultCount;

        return new FirestationResponse(persons, adultCount, childCount);
    }
}