package com.openclassrooms.safetynet.model;

import jakarta.validation.constraints.NotNull;

/**
 * Représente une personne recensée dans le système SafetyNet.
 *
 * @param firstName le prénom de la personne (obligatoire)
 * @param lastName  le nom de la personne (obligatoire)
 * @param address   l'adresse du domicile (obligatoire)
 * @param city      la ville de résidence (obligatoire)
 * @param zip       le code postal (obligatoire)
 * @param phone     le numéro de téléphone (obligatoire)
 * @param email     l'adresse email (obligatoire)
 */
public record Person(
        @NotNull(message = "Le prenom ne peut pas etre nul") String firstName,
        @NotNull String lastName,
        @NotNull String address,
        @NotNull String city,
        @NotNull String zip,
        @NotNull String phone,
        @NotNull String email
) {}