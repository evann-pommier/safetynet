package com.openclassrooms.safetynet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.model.SafetyNetData;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
public class DataService {

    private SafetyNetData data;

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
     * Retourne la liste des personnes.
     *
     * @return liste mutable de {@link Person}
     */
    public List<Person> getPersons() {
        return data.getPersons();
    }

    /**
     * Retourne la liste des mappings adresse/caserne.
     *
     * @return liste mutable de {@link Firestation}
     */
    public List<Firestation> getFirestations() {
        return data.getFirestations();
    }

    /**
     * Retourne la liste des dossiers médicaux.
     *
     * @return liste mutable de {@link MedicalRecord}
     */
    public List<MedicalRecord> getMedicalRecords() {
        return data.getMedicalRecords();
    }
}