package com.openclassrooms.safetynet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.record.PersonInfoResponse;
import com.openclassrooms.safetynet.util.AgeCalculator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonInfoService {

    private final DataService dataService;

    public List<PersonInfoResponse> getByLastName(String lastName) {

        log.debug("Searching persons with lastName={}", lastName);

        List<PersonInfoResponse> result = dataService.getPersons().stream()
                .filter(p -> p.lastName().equalsIgnoreCase(lastName))
                .map(p -> {

                    log.debug("Processing person: {} {}", p.firstName(), p.lastName());

                    MedicalRecord medical = dataService.getMedicalRecords().stream()
                            .filter(m -> m.firstName().equals(p.firstName()) &&
                                         m.lastName().equals(p.lastName()))
                            .findFirst()
                            .orElse(null);

                    int age = 0;
                    List<String> medications = List.of();
                    List<String> allergies = List.of();

                    if (medical != null) {
                        age = AgeCalculator.calculateAge(medical.birthdate());
                        medications = medical.medications();
                        allergies = medical.allergies();

                        log.debug("Medical data found for {} {}", p.firstName(), p.lastName());
                    } else {
                        log.debug("No medical data found for {} {}", p.firstName(), p.lastName());
                    }

                    return new PersonInfoResponse(
                            p.firstName(),
                            p.lastName(),
                            p.address(),
                            age,
                            p.email(),
                            medications,
                            allergies
                    );
                })
                .toList();

        log.info("Found {} persons for lastName={}", result.size(), lastName);

        return result;
    }
}