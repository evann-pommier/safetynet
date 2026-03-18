package com.openclassrooms.safetynet.record;

import java.util.List;
import java.util.Map;

/**
 * Représente la réponse pour l'endpoint /flood.
 * Regroupe les habitants par adresse pour les casernes demandées.
 *
 * @param households map dont la clé est l'adresse et la valeur
 *                   est la liste des habitants avec leurs informations médicales
 */
public record FloodResponse(Map<String, List<FireResponse>> households) {}