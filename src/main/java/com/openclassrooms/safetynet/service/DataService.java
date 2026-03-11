package com.openclassrooms.safetynet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.SafetyNetData;

import jakarta.annotation.PostConstruct;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class DataService {

    private SafetyNetData data;

    @PostConstruct
    public void loadData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        data = mapper.readValue(new File("data.json"), SafetyNetData.class);
    }

    public List<Person> getPersons() {
        return data.getPersons();
    }

    public List<Firestation> getFirestations() {
        return data.getFirestations();
    }

    public List<MedicalRecord> getMedicalRecords() {
        return data.getMedicalrecords();
    }
}