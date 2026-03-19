package com.openclassrooms.safetynet.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Représente le mapping entre une adresse et le numéro de la caserne de pompiers
 * qui la dessert.
 *
 * @param address l'adresse couverte
 * @param station le numéro de la caserne de pompiers
 */
public record Firestation(
        @NotBlank(message = "L'adresse ne peut pas être vide") String address,
        @Pattern(regexp = "\\d+", message = "Le numéro de caserne doit être un entier positif") String station
) {}