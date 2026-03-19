package com.openclassrooms.safetynet.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

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
public record MedicalRecord(
        @NotBlank(message = "Le prénom ne peut pas être vide") String firstName,
        @NotBlank(message = "Le nom ne peut pas être vide") String lastName,
        @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "La date de naissance doit être au format MM/dd/yyyy") String birthdate,
        List<String> medications,
        List<String> allergies
) {}