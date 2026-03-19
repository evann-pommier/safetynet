package com.openclassrooms.safetynet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;

/**
 * Gestionnaire global des exceptions pour l'ensemble des contrôleurs REST.
 * Intercepte les exceptions non gérées et retourne des réponses HTTP appropriées.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Gère les arguments invalides passés aux méthodes.
     *
     * @param e l'exception levée
     * @return 400 Bad Request avec le message de l'exception
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        log.error("Invalid argument: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    /**
     * Gère les erreurs de format numérique, par exemple un numéro de caserne non entier.
     *
     * @param e l'exception levée
     * @return 400 Bad Request avec un message générique
     */
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleNumberFormat(NumberFormatException e) {
        log.error("Number format error: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid number format");
    }

    /**
     * Gère toutes les exceptions non prévues par les handlers spécifiques.
     *
     * @param e l'exception levée
     * @return 500 Internal Server Error avec un message générique
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception e) {
        log.error("Unexpected error: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
}