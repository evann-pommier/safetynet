package com.openclassrooms.safetynet.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

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
        @NotBlank(message = "Le prénom ne peut pas être vide") String firstName,
        @NotBlank(message = "Le nom ne peut pas être vide") String lastName,
        @NotBlank(message = "L'adresse ne peut pas être vide") String address,
        @NotBlank(message = "La ville ne peut pas être vide") String city,
        @Pattern(regexp = "\\d{5}", message = "Le code postal doit contenir 5 chiffres") String zip,
        @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}", message = "Le téléphone doit être au format XXX-XXX-XXXX") String phone,
        @Email(message = "L'adresse email n'est pas valide") @NotBlank(message = "L'email ne peut pas être vide") String email
) {}