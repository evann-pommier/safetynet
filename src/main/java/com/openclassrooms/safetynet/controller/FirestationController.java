package com.openclassrooms.safetynet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.record.FirestationResponse;
import com.openclassrooms.safetynet.service.FirestationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Contrôleur REST pour l'endpoint /firestation.
 * Permet de consulter les personnes couvertes par une caserne,
 * et de gérer les mappings adresse/caserne (ajout, mise à jour, suppression).
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/firestation")
public class FirestationController {

    private final FirestationService firestationService;

    /**
     * Retourne la liste des personnes couvertes par la caserne donnée,
     * ainsi que le décompte des adultes et des enfants.
     *
     * @param stationNumber le numéro de la caserne
     * @return {@link FirestationResponse} contenant les personnes, le nombre d'adultes et d'enfants
     */
    @GetMapping
    public ResponseEntity<FirestationResponse> getPersonsByStation(@RequestParam int stationNumber) {
        log.info("GET /firestation called with stationNumber={}", stationNumber);
        FirestationResponse response = firestationService.getPersonsByStation(stationNumber);
        return ResponseEntity.ok(response);
    }

    /**
     * Ajoute un nouveau mapping adresse/caserne.
     *
     * @param firestation le mapping à ajouter
     * @return 201 si ajouté, 409 si le mapping existe déjà
     */
    @PostMapping
    public ResponseEntity<String> addMapping(@RequestBody Firestation firestation) {
        log.info("POST /firestation called with: {}", firestation);
        boolean added = firestationService.addMapping(firestation);
        if (added) {
            log.info("Mapping added successfully: {}", firestation);
            return ResponseEntity.status(HttpStatus.CREATED).body("Mapping added");
        } else {
            log.warn("Mapping already exists: {}", firestation);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Mapping already exists");
        }
    }

    /**
     * Met à jour le numéro de caserne associé à une adresse.
     *
     * @param firestation le mapping avec l'adresse existante et le nouveau numéro de caserne
     * @return 200 si mis à jour, 404 si le mapping n'existe pas
     */
    @PutMapping
    public ResponseEntity<String> updateMapping(@RequestBody Firestation firestation) {
        log.info("PUT /firestation called with: {}", firestation);
        boolean updated = firestationService.updateMapping(firestation);
        if (updated) {
            log.info("Mapping updated successfully: {}", firestation);
            return ResponseEntity.ok("Mapping updated");
        } else {
            log.warn("Mapping not found for address: {}", firestation.address());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mapping not found");
        }
    }

    /**
     * Supprime le mapping correspondant à l'adresse et au numéro de caserne donnés.
     *
     * @param address l'adresse du mapping à supprimer
     * @param station le numéro de caserne du mapping à supprimer
     * @return 200 si supprimé, 404 si le mapping n'existe pas
     */
    @DeleteMapping
    public ResponseEntity<String> deleteMapping(@RequestParam String address, @RequestParam String station) {
        log.info("DELETE /firestation called for address: {} station: {}", address, station);
        boolean deleted = firestationService.deleteMapping(address, station);
        if (deleted) return ResponseEntity.ok("Mapping deleted");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mapping not found");
    }
}