package com.example.inventory.control.controllers;

import com.example.inventory.control.facades.RemainingFacade;
import com.example.inventory.control.ui.models.responses.remaining.RemainingResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Validated
@RestController
@RequestMapping("/remaining")
public class RemainingController {

    private static final Logger LOGGER = Logger.getLogger(RemainingController.class.getName());

    private final RemainingFacade remainingFacade;

    public RemainingController(RemainingFacade remainingFacade) {
        this.remainingFacade = remainingFacade;
    }

    @GetMapping
    public ResponseEntity<RemainingResponse> getAllRemaining() {
        LOGGER.info("Запрос на получение всех остатков.");
        RemainingResponse response = remainingFacade.getListAllRemaining();
        LOGGER.info("Запрос на получение всех остатков выполнен успешно.");
        return ResponseEntity.ok(response);
    }

}
