package com.openclassrooms.safetynet.record;

import java.util.List;

/**
 * Représente la réponse pour l'endpoint /firestation.
 * Contient la liste des personnes couvertes par une caserne,
 * ainsi que le décompte des adultes et des enfants.
 *
 * @param persons    la liste des personnes couvertes par la caserne
 * @param adultCount le nombre d'adultes (plus de 18 ans)
 * @param childCount le nombre d'enfants (18 ans ou moins)
 */
public record FirestationResponse(List<PersonResponse> persons, int adultCount, int childCount) {}