package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;

import java.util.List;

/**
 * Interface définissant le contrat d'accès aux données de l'application.
 * Permet de découpler les services métier de l'implémentation concrète
 * du chargement des données.
 */
public interface IDataService {

    /**
     * Retourne la liste des personnes.
     *
     * @return liste mutable de {@link Person}
     */
    List<Person> getPersons();

    /**
     * Retourne la liste des mappings adresse/caserne.
     *
     * @return liste mutable de {@link Firestation}
     */
    List<Firestation> getFirestations();

    /**
     * Retourne la liste des dossiers médicaux.
     *
     * @return liste mutable de {@link MedicalRecord}
     */
    List<MedicalRecord> getMedicalRecords();
}