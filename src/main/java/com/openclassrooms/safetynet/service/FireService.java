package com.openclassrooms.safetynet.service;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.openclassrooms.safetynet.record.FireResponse;
import com.openclassrooms.safetynet.util.AgeCalculator;

/**
 * Service gérant la logique métier pour l'endpoint /fire.
 * Permet de récupérer les habitants d'une adresse avec leurs informations médicales
 * et la ou les casernes qui les desservent.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FireService {

    private final IDataService dataService;

    /**
     * Retourne la liste des habitants vivant à l'adresse donnée,
     * avec leur âge, leurs médicaments, leurs allergies
     * et le(s) numéro(s) de caserne desservant cette adresse.
     *
     * @param address l'adresse recherchée
     * @return liste de {@link FireResponse}, vide si aucun habitant trouvé
     */
    public List<FireResponse> getFireInfoByAddress(String address) {
        log.info("Fetching fire info for address: {}", address);

        List<String> stations = dataService.getFirestations().stream()
                .filter(f -> f.address().equals(address))
                .map(f -> f.station())
                .toList();

        List<FireResponse> responses = dataService.getPersons().stream()
                .filter(p -> p.address().equals(address))
                .map(p -> {
                    var medical = dataService.getMedicalRecords().stream()
                            .filter(m -> m.firstName().equals(p.firstName()) &&
                                         m.lastName().equals(p.lastName()))
                            .findFirst()
                            .orElse(null);

                    if (medical == null) {
                        log.warn("No medical record found for {} {}", p.firstName(), p.lastName());
                    }

                    int age = medical != null ?
                            AgeCalculator.calculateAge(medical.birthdate()) : 0;

                    return new FireResponse(
                            p.firstName(),
                            p.lastName(),
                            p.phone(),
                            age,
                            medical != null ? medical.medications() : List.of(),
                            medical != null ? medical.allergies() : List.of(),
                            stations
                    );
                })
                .toList();

        log.info("Found {} residents at address: {}", responses.size(), address);
        return responses;
    }
}