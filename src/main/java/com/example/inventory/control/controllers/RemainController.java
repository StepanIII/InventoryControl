package com.example.inventory.control.controllers;

import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.warehouse.RemainsResponseBody;
import com.example.inventory.control.facades.RemainingFacade;
import com.example.inventory.control.api.remain.RemainingResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Validated
@RestController
@RequestMapping("/remain")
public class RemainController {

    private static final Logger LOGGER = Logger.getLogger(RemainController.class.getName());

    private final RemainingFacade remainingFacade;

    public RemainController(RemainingFacade remainingFacade) {
        this.remainingFacade = remainingFacade;
    }

    @GetMapping
    public ResponseEntity<RemainingResponseBody> getAllRemaining() {
        LOGGER.info("Запрос на получение всех остатков.");
        RemainingResponseBody response = remainingFacade.getAllRemaining();
        LOGGER.info(String.format("Запрос на получение всех остатков выполнен успешно. Количество: %d.", response.getRemaining().size()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{warehouseId}")
    public ResponseEntity<RemainsResponseBody> getAllWarehouseRemains(@PathVariable Long warehouseId) {
        LOGGER.info(String.format("Запрос на получение всех остатков по складу. 'warehouseId: %d'.", warehouseId));
        RemainsResponseBody response = remainingFacade.getAllRemainsByWarehouseId(warehouseId);
        if (response.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format(
                    "Запрос на получение всех остатков по складу выполнен успешно. warehouseId: %d, Количество: %d.",
                    warehouseId, response.getRemains().size()));
        } else {
            LOGGER.info(String.format(
                    "Запрос на получение всех остатков по складу не выполнен успешно. Причина: %s.", response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

}
