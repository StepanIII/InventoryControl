package com.example.inventory.control.controllers;

import com.example.inventory.control.facades.BenefactorFacade;
import com.example.inventory.control.api.benefactor.BenefactorsResponse;
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
        BenefactorsResponse response = benefactorFacade.getListAllBenefactors();
        LOGGER.info(String.format("Запрос на получение всех благодетелей выполнен успешно. Количество: %d.", response.getBenefactors().size()));
        return ResponseEntity.ok(response);
    }

}
