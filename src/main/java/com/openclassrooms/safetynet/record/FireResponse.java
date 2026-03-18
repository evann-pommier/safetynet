package com.openclassrooms.safetynet.record;

import java.util.List;

/**
 * Représente les informations d'un habitant retournées par les endpoints /fire et /flood.
 * Inclut les informations médicales et la ou les casernes desservant son adresse.
 *
 * @param firstName   le prénom de la personne
 * @param lastName    le nom de la personne
 * @param phone       le numéro de téléphone
 * @param age         l'âge de la personne
 * @param medications la liste des médicaments avec leur posologie
 * @param allergies   la liste des allergies
 * @param stations    la liste des numéros de casernes desservant l'adresse
 */
public record FireResponse(String firstName, String lastName, String phone, int age, List<String> medications,
        List<String> allergies, List<String> stations) {}