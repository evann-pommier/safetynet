package com.openclassrooms.safetynet.model;

import java.util.List;
import lombok.Data;

/**
 * Représente la structure racine des données chargées depuis le fichier data.json.
 * Contient l'ensemble des personnes, des mappings caserne/adresse
 * et des dossiers médicaux.
 */
@Data
public class SafetyNetData {

    /** Liste de toutes les personnes. */
    private List<Person> persons;

    /** Liste de tous les mappings adresse/caserne. */
    private List<Firestation> firestations;

    /** Liste de tous les dossiers médicaux. */
    private List<MedicalRecord> medicalRecords;
}