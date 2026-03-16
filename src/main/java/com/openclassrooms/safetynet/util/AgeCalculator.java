package com.openclassrooms.safetynet.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class AgeCalculator {
	private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static int calculateAge(String birthdate) {

        if (birthdate == null || birthdate.isEmpty()) {
            return 0;
        }

        LocalDate birthDate = LocalDate.parse(birthdate, FORMATTER);
        LocalDate today = LocalDate.now();

        return Period.between(birthDate, today).getYears();
    }
}
