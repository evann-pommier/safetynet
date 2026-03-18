package com.openclassrooms.safetynet.record;

/**
 * Représente une réponse simplifiée d'une personne.
 * Utilisée notamment par les endpoints /firestation et /childAlert.
 *
 * @param firstName le prénom de la personne
 * @param lastName  le nom de la personne
 * @param address   l'adresse du domicile
 * @param phone     le numéro de téléphone
 */
public record PersonResponse(String firstName, String lastName, String address, String phone) {}