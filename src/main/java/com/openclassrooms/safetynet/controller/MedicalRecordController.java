package com.openclassrooms.safetynet.controller;

import java.util.List;
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
import com.openclassrooms.safetynet.service.MedicalRecordService;
import com.openclassrooms.safetynet.model.MedicalRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Contrôleur REST pour l'endpoint /medicalRecord.
 * Permet de consulter et de gérer les dossiers médicaux
 * (ajout, mise à jour, suppression).
 */
@RestController
@RequestMapping("/medicalRecord")
@RequiredArgsConstructor
@Slf4j
public class MedicalRecordController {

    private final MedicalRecordService service;

    /**
     * Retourne la liste de tous les dossiers médicaux.
     *
     * @return liste de {@link MedicalRecord}
     */
    @GetMapping
    public List<MedicalRecord> getAll() {
        log.info("GET /medicalRecord called");
        return service.getAllMedicalRecords();
    }

    /**
     * Ajoute un nouveau dossier médical.
     *
     * @param record le dossier médical à ajouter
     * @return 201 si ajouté avec succès
     */
    @PostMapping
    public ResponseEntity<String> add(@RequestBody MedicalRecord record) {
        log.info("POST /medicalRecord called for {} {}", record.firstName(), record.lastName());
        service.addMedicalRecord(record);
        return ResponseEntity.status(HttpStatus.CREATED).body("Medical record added");
    }

    /**
     * Met à jour un dossier médical existant.
     * Le prénom et le nom servent d'identifiant unique.
     *
     * @param record le dossier médical avec les nouvelles données
     * @return 200 si mis à jour, 404 si le dossier n'existe pas
     */
    @PutMapping
    public ResponseEntity<String> update(@RequestBody MedicalRecord record) {
        log.info("PUT /medicalRecord called for {} {}", record.firstName(), record.lastName());
        boolean updated = service.updateMedicalRecord(record);
        if (!updated) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medical record not found");
        return ResponseEntity.ok("Medical record updated");
    }

    /**
     * Supprime le dossier médical correspondant au prénom et nom donnés.
     *
     * @param firstName le prénom de la personne
     * @param lastName  le nom de la personne
     * @return 200 si supprimé, 404 si le dossier n'existe pas
     */
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam String firstName, @RequestParam String lastName) {
        log.info("DELETE /medicalRecord called for {} {}", firstName, lastName);
        boolean deleted = service.deleteMedicalRecord(firstName, lastName);
        if (!deleted) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medical record not found");
        return ResponseEntity.ok("Medical record deleted");
    }
}