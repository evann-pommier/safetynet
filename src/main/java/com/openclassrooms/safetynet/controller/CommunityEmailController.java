package com.openclassrooms.safetynet.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.safetynet.service.CommunityEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Contrôleur REST pour l'endpoint /communityEmail.
 * Permet de récupérer les adresses email de tous les habitants d'une ville.
 */
@RestController
@RequestMapping("/communityEmail")
@RequiredArgsConstructor
@Slf4j
public class CommunityEmailController {

    private final CommunityEmailService service;

    /**
     * Retourne la liste des adresses email de tous les habitants de la ville donnée.
     *
     * @param city la ville recherchée
     * @return liste des emails, sans doublons
     */
    @GetMapping
    public List<String> getEmails(@RequestParam String city) {
        log.info("Requête emails pour la ville {}", city);
        log.debug("Delegating to communityEmailService.getEmailsByCity({})", city);
        List<String> emails = service.getEmailsByCity(city);
        log.debug("communityEmailService returned {} emails", emails.size());
        log.info("{} emails trouvés pour la ville {}", emails.size(), city);
        return emails;
    }
}