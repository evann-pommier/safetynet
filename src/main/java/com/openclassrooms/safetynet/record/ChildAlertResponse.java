package com.openclassrooms.safetynet.record;

import java.util.List;

/**
 * Représente la réponse pour l'endpoint /childAlert.
 * Contient les informations d'un enfant et les autres membres de son foyer.
 *
 * @param firstName       le prénom de l'enfant
 * @param lastName        le nom de l'enfant
 * @param age             l'âge de l'enfant
 * @param householdMembers la liste des autres membres du foyer
 */
public record ChildAlertResponse(String firstName, String lastName, int age, List<PersonResponse> householdMembers) {}