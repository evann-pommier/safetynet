package com.openclassrooms.safetynet.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.safetynet.record.ChildAlertResponse;
import com.openclassrooms.safetynet.service.ChildAlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Contrôleur REST pour l'endpoint /childAlert.
 * Permet de récupérer la liste des enfants habitant à une adresse donnée,
 * ainsi que les autres membres du foyer.
 */
@RestController
@RequestMapping("/childAlert")
@RequiredArgsConstructor
@Slf4j
public class ChildAlertController {

    private final ChildAlertService childAlertService;

    /**
     * Retourne la liste des enfants (18 ans ou moins) habitant à l'adresse donnée,
     * accompagnés des autres membres du foyer.
     *
     * @param address l'adresse à rechercher
     * @return liste de {@link ChildAlertResponse}, vide si aucun enfant trouvé
     */
    @GetMapping
    public List<ChildAlertResponse> getChildrenByAddress(@RequestParam String address) {
        log.info("Request received for /childAlert with address: {}", address);
        log.debug("Delegating to childAlertService.getChildrenByAddress({})", address);
        List<ChildAlertResponse> response = childAlertService.getChildrenByAddress(address);
        log.debug("childAlertService returned {} results", response.size());
        log.info("Response returned: {} children found", response.size());
        return response;
    }
}