package com.example.inventory.control.controllers;

import com.example.inventory.control.facades.AcceptanceFacade;
import com.example.inventory.control.ui.models.responses.acceptance.AcceptanceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Validated
@RestController
@RequestMapping("/acceptance")
public class AcceptanceController {

    private static final Logger LOGGER = Logger.getLogger(AcceptanceController.class.getName());

    private final AcceptanceFacade acceptanceFacade;

    public AcceptanceController(AcceptanceFacade acceptanceFacade) {
        this.acceptanceFacade = acceptanceFacade;
    }

    @GetMapping
    public ResponseEntity<AcceptanceResponse> getAllAcceptance() {
        LOGGER.info("Запрос на получение всех приемок.");
        AcceptanceResponse acceptanceResponse = acceptanceFacade.getListAllAcceptance();
        LOGGER.info("Запрос на получение всех приемок выполнен успешно.");
        return ResponseEntity.ok(acceptanceResponse);
    }

}
