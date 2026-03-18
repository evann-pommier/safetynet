package com.openclassrooms.safetynet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.record.ChildAlertResponse;
import com.openclassrooms.safetynet.record.PersonResponse;
import com.openclassrooms.safetynet.util.AgeCalculator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChildAlertService {

    private final DataService dataService;

    
    private int getAge(Person p) {
        return AgeCalculator.calculateAge(
            dataService.getMedicalRecords().stream()
                .filter(m -> m.firstName().equals(p.firstName()) &&
                             m.lastName().equals(p.lastName()))
                .findFirst()
                .map(m -> m.birthdate())
                .orElse("01/01/1900")
        );
    }
    
    public List<ChildAlertResponse> getChildrenByAddress(String address) {
        log.debug("Getting children for address {}", address);

        List<Person> residents = dataService.getPersons().stream()
                .filter(p -> p.address().equals(address))
                .toList();

        return residents.stream()
                .filter(p -> getAge(p) <= 18)
                .map(p -> {
                    List<PersonResponse> household = residents.stream()
                            .filter(o -> !o.equals(p))
                            .map(o -> new PersonResponse(o.firstName(), o.lastName(), o.address(), o.phone()))
                            .toList();
                    return new ChildAlertResponse(p.firstName(), p.lastName(), getAge(p), household);
                })
                .toList();
    }
}