package com.openclassrooms.safetynet.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.safetynet.service.PersonInfoService;
import com.openclassrooms.safetynet.record.PersonInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Contrôleur REST pour l'endpoint /personInfo.
 * Permet de récupérer les informations détaillées d'une ou plusieurs personnes
 * à partir de leur nom de famille.
 */
@RestController
@RequestMapping("/personInfo")
@RequiredArgsConstructor
@Slf4j
public class PersonInfoController {

    private final PersonInfoService service;

    /**
     * Retourne les informations de toutes les personnes portant le nom donné.
     * Si plusieurs personnes partagent le même nom, elles apparaissent toutes.
     *
     * @param lastName le nom de famille recherché
     * @return liste de {@link PersonInfoResponse} contenant nom, adresse, âge,
     *         email, médicaments et allergies
     */
    @GetMapping
    public List<PersonInfoResponse> getInfo(@RequestParam String lastName) {
        log.info("Request person info for lastName={}", lastName);
        log.debug("Delegating to personInfoService.getByLastName({})", lastName);
        List<PersonInfoResponse> response = service.getByLastName(lastName);
        log.debug("personInfoService returned {} results", response.size());
        log.info("Response returned: {} persons found for lastName={}", response.size(), lastName);
        return response;
    }
}