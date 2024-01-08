package com.example.inventory.control.controllers;

import com.example.inventory.control.facades.BenefactorFacade;
import com.example.inventory.control.ui.models.responses.benefactor.BenefactorsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Validated
@RestController
@RequestMapping("/benefactor")
public class BenefactorController {

    private static final Logger LOGGER = Logger.getLogger(BenefactorController.class.getName());

    private final BenefactorFacade benefactorFacade;

    public BenefactorController(BenefactorFacade benefactorFacade) {
        this.benefactorFacade = benefactorFacade;
    }

    @GetMapping
    public ResponseEntity<BenefactorsResponse> getAllBenefactors() {
        LOGGER.info("Запрос на получение всех благодетелей.");
        BenefactorsResponse benefactorsResponse = benefactorFacade.getListAllBenefactors();
        LOGGER.info("Запрос на получение всех благодетелей выполнен успешно.");
        return ResponseEntity.ok(benefactorsResponse);
    }

}
