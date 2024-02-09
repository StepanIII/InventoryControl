package com.example.inventory.control.controllers;

import com.example.inventory.control.api.client.benefactor.BeneficiariesResponseBody;
import com.example.inventory.control.facades.ClientFacade;
import com.example.inventory.control.api.client.benefactor.BenefactorsResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Validated
@RestController
@RequestMapping("/client")
public class ClientController {

    private static final Logger LOGGER = Logger.getLogger(ClientController.class.getName());

    private final ClientFacade CLientFacade;

    public ClientController(ClientFacade CLientFacade) {
        this.CLientFacade = CLientFacade;
    }

    @GetMapping("/benefactor")
    public ResponseEntity<BenefactorsResponseBody> getAllBenefactors() {
        LOGGER.info("Запрос на получение всех благодетелей.");
        BenefactorsResponseBody responseBody = CLientFacade.getAllBenefactors();
        LOGGER.info(String.format("Запрос на получение всех благодетелей выполнен успешно. Количество: %d.", responseBody.getBenefactors().size()));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/beneficiary")
    public ResponseEntity<BeneficiariesResponseBody> getAllBeneficiaries() {
        LOGGER.info("Запрос на получение всех благополучателей.");
        BeneficiariesResponseBody responseBody = CLientFacade.getAllBeneficiaries();
        LOGGER.info(String.format("Запрос на получение всех благополучателей выполнен успешно. Количество: %d.", responseBody.getBeneficiaries().size()));
        return ResponseEntity.ok(responseBody);
    }

}
