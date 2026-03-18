package com.openclassrooms.safetynet.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.safetynet.service.PhoneAlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Contrôleur REST pour l'endpoint /phoneAlert.
 * Permet de récupérer les numéros de téléphone des habitants
 * desservis par une caserne de pompiers donnée.
 */
@RestController
@RequestMapping("/phoneAlert")
@RequiredArgsConstructor
@Slf4j
public class PhoneAlertController {

    private final PhoneAlertService service;

    /**
     * Retourne la liste des numéros de téléphone des résidents
     * couverts par la caserne donnée, sans doublons.
     *
     * @param firestation le numéro de la caserne
     * @return liste des numéros de téléphone
     */
    @GetMapping
    public List<String> getPhones(@RequestParam int firestation) {
        log.info("Request phoneAlert for station {}", firestation);
        return service.getPhonesByStation(firestation);
    }
}