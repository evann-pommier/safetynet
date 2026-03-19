package com.openclassrooms.safetynet.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.openclassrooms.safetynet.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service gérant la logique métier pour l'endpoint /communityEmail.
 * Permet de récupérer les adresses email de tous les habitants d'une ville.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommunityEmailService {

    private final IDataService dataService;

    /**
     * Retourne la liste des adresses email de tous les habitants de la ville donnée,
     * sans doublons.
     *
     * @param city la ville recherchée
     * @return liste des emails distincts
     */
    public List<String> getEmailsByCity(String city) {
        log.debug("Récupération des emails pour la ville : {}", city);

        List<String> emails = dataService.getPersons().stream()
                .filter(p -> p.city().equalsIgnoreCase(city))
                .map(Person::email)
                .distinct()
                .toList();

        log.info("{} emails trouvés pour la ville {}", emails.size(), city);
        return emails;
    }
}