package com.openclassrooms.safetynet.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Utilitaire pour le calcul de l'âge à partir d'une date de naissance.
 */
public class AgeCalculator {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Calcule l'âge en années à partir d'une date de naissance au format MM/dd/yyyy.
     * Retourne 0 si la date est nulle ou vide.
     *
     * @param birthdate la date de naissance au format MM/dd/yyyy
     * @return l'âge en années, ou 0 si la date est invalide ou absente
     */
    public static int calculateAge(String birthdate) {
        if (birthdate == null || birthdate.isEmpty()) {
            return 0;
        }
        LocalDate birthDate = LocalDate.parse(birthdate, FORMATTER);
        LocalDate today = LocalDate.now();
        return Period.between(birthDate, today).getYears();
    }
}