package com.openclassrooms.safetynet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.Person;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunityEmailService {

    private final DataService dataService;

    /**
     * Retourne la liste des emails de tous les habitants d'une ville.
     *
     * @param city La ville recherchée
     * @return List<String> emails
     */
    public List<String> getEmailsByCity(String city) {
        log.debug("Récupération des emails pour la ville : {}", city);

        List<String> emails = dataService.getPersons().stream()
                .filter(p -> p.city().equalsIgnoreCase(city))
                .map(Person::email)
                .distinct() // éviter les doublons
                .toList();

        log.info("{} emails trouvés pour la ville {}", emails.size(), city);
        return emails;
    }
}