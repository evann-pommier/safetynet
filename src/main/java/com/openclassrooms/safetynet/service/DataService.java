package com.openclassrooms.safetynet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.SafetyNetData;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
public class DataService {

	private SafetyNetData data;

	@PostConstruct
	public void loadData() throws IOException {
	    ObjectMapper mapper = new ObjectMapper();
	    InputStream is = getClass().getClassLoader().getResourceAsStream("data.json");

	    if (is == null) {
	        throw new RuntimeException("data.json not found in resources folder");
	    }

	    this.data = mapper.readValue(is, SafetyNetData.class);
	    log.info("JSON data loaded successfully.");
	}

	public List<Person> getPersons() {
		return data.getPersons();
	}

	public List<Firestation> getFirestations() {
		return data.getFirestations();
	}

	public List<MedicalRecord> getMedicalRecords() {
		return data.getMedicalRecords();
	}

}