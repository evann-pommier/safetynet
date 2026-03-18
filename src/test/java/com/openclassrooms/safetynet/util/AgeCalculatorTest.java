package com.openclassrooms.safetynet.util;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class AgeCalculatorTest {

    @Test
    void calculateAge_shouldReturnCorrectAge() {
        assertThat(AgeCalculator.calculateAge("01/01/1990")).isGreaterThan(0);
    }

    @Test
    void calculateAge_shouldReturnZero_whenBirthdateIsNull() {
        assertThat(AgeCalculator.calculateAge(null)).isEqualTo(0);
    }

    @Test
    void calculateAge_shouldReturnZero_whenBirthdateIsEmpty() {
        assertThat(AgeCalculator.calculateAge("")).isEqualTo(0);
    }

    @Test
    void calculateAge_shouldReturnCorrectAge_forChild() {
        assertThat(AgeCalculator.calculateAge("01/01/2015")).isLessThanOrEqualTo(18);
    }
}