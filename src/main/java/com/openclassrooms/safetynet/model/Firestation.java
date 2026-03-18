package com.openclassrooms.safetynet.model;

/**
 * Représente le mapping entre une adresse et le numéro de la caserne de pompiers
 * qui la dessert.
 *
 * @param address l'adresse couverte
 * @param station le numéro de la caserne de pompiers
 */
public record Firestation(String address, String station) {}