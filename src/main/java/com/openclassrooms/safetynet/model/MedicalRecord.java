package com.openclassrooms.safetynet.model;

import java.util.List;

/**
 * Représente le dossier médical d'une personne.
 *
 * @param firstName   le prénom de la personne
 * @param lastName    le nom de la personne
 * @param birthdate   la date de naissance au format MM/dd/yyyy
 * @param medications la liste des médicaments avec leur posologie
 * @param allergies   la liste des allergies
 */
public record MedicalRecord(String firstName, String lastName, String birthdate, List<String> medications, List<String> allergies) {}