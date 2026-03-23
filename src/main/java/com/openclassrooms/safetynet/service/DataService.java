package com.openclassrooms.safetynet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.model.SafetyNetData;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Service responsable du chargement et de l'accès aux données de l'application.
 * Les données sont chargées depuis le fichier data.json au démarrage de l'application
 * et maintenues en mémoire pendant toute la durée de vie du service.
 */
@Service
@Slf4j
public class DataService implements IDataService {

    private SafetyNetData data;
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Charge les données depuis le fichier data.json au démarrage de l'application.
     *
     * @throws IOException si le fichier ne peut pas être lu
     * @throws RuntimeException si le fichier data.json est introuvable dans les ressources
     */
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
    
    /**
     * Sauvegarde l'état actuel des données dans le fichier data.json.
     * Appelée après chaque opération de modification (POST, PUT, DELETE).
     *
     * @throws IOException si le fichier ne peut pas être écrit
     */
    public void saveData() throws IOException {
        File file = ResourceUtils.getFile("classpath:data.json");
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
        log.info("JSON data saved successfully.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Person> getPersons() {
        return data.getPersons();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Firestation> getFirestations() {
        return data.getFirestations();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MedicalRecord> getMedicalRecords() {
        return data.getMedicalRecords();
    }
}