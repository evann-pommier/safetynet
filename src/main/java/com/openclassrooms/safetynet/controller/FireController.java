package com.openclassrooms.safetynet.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.safetynet.record.FireResponse;
import com.openclassrooms.safetynet.service.FireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Contrôleur REST pour l'endpoint /fire.
 * Permet de récupérer les habitants d'une adresse ainsi que
 * la caserne de pompiers qui la dessert.
 */
@RestController
@RequestMapping("/fire")
@RequiredArgsConstructor
@Slf4j
public class FireController {

    private final FireService service;

    /**
     * Retourne la liste des habitants vivant à l'adresse donnée,
     * avec leurs informations médicales et la caserne les desservant.
     *
     * @param address l'adresse recherchée
     * @return liste de {@link FireResponse} contenant nom, téléphone, âge,
     *         médicaments, allergies et numéro(s) de caserne
     */
    @GetMapping
    public List<FireResponse> getFireInfo(@RequestParam String address) {
        log.info("Request fire info for {}", address);
        log.debug("Delegating to fireService.getFireInfoByAddress({})", address);
        List<FireResponse> response = service.getFireInfoByAddress(address);
        log.debug("fireService returned {} residents", response.size());
        log.info("Response returned: {} residents found for address {}", response.size(), address);
        return response;
    }
}