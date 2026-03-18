package com.openclassrooms.safetynet.record;

import java.util.List;

/**
 * Représente la réponse pour l'endpoint /personInfo.
 * Contient les informations détaillées d'une personne, incluant ses antécédents médicaux.
 *
 * @param firstName   le prénom de la personne
 * @param lastName    le nom de la personne
 * @param address     l'adresse du domicile
 * @param age         l'âge de la personne
 * @param email       l'adresse email
 * @param medications la liste des médicaments avec leur posologie
 * @param allergies   la liste des allergies
 */
public record PersonInfoResponse(String firstName, String lastName, String address, int age, String email,
        List<String> medications, List<String> allergies) {}