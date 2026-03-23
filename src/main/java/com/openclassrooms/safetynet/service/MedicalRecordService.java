package com.openclassrooms.safetynet.service;

import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import com.openclassrooms.safetynet.model.MedicalRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service gérant la logique métier pour l'endpoint /medicalRecord.
 * Permet de consulter et de gérer les dossiers médicaux
 * (ajout, mise à jour, suppression).
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalRecordService {

    private final IDataService dataService;

    /**
     * Retourne la liste de tous les dossiers médicaux.
     *
     * @return liste de {@link MedicalRecord}
     */
    public List<MedicalRecord> getAllMedicalRecords() {
        log.debug("Fetching all medical records");
        return dataService.getMedicalRecords();
    }

    /**
     * Ajoute un nouveau dossier médical.
     *
     * @param record le dossier médical à ajouter
     */
    public void addMedicalRecord(MedicalRecord record) {
        dataService.getMedicalRecords().add(record);
        save();
        log.info("Added medical record for {} {}", record.firstName(), record.lastName());
    }

    /**
     * Met à jour un dossier médical existant.
     * Le prénom et le nom servent d'identifiant unique.
     *
     * @param record le dossier médical avec les nouvelles données
     * @return true si mis à jour, false si le dossier n'existe pas
     */
    public boolean updateMedicalRecord(MedicalRecord record) {
        boolean updated = dataService.getMedicalRecords().removeIf(r ->
            r.firstName().equals(record.firstName()) && r.lastName().equals(record.lastName())
        );
        if (updated) {
            dataService.getMedicalRecords().add(record);
            save();
            log.info("Updated medical record for {} {}", record.firstName(), record.lastName());
        } else {
            log.warn("Medical record not found for {} {}", record.firstName(), record.lastName());
        }
        return updated;
    }

    /**
     * Supprime le dossier médical correspondant au prénom et nom donnés.
     *
     * @param firstName le prénom de la personne
     * @param lastName  le nom de la personne
     * @return true si supprimé, false si le dossier n'existe pas
     */
    public boolean deleteMedicalRecord(String firstName, String lastName) {
        boolean deleted = dataService.getMedicalRecords().removeIf(r ->
            r.firstName().equals(firstName) && r.lastName().equals(lastName)
        );
        if (deleted) {
            save();
            log.info("Deleted medical record for {} {}", firstName, lastName);
        } else {
            log.warn("Medical record not found for {} {}", firstName, lastName);
        }
        return deleted;
    }
    
    private void save() {
        try {
            dataService.saveData();
        } catch (IOException e) {
            log.error("Failed to save data: {}", e.getMessage());
        }
    }
}