package com.example.inventory.control.controllers;

import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.warehouse.RemainsResponseBody;
import com.example.inventory.control.facades.WarehouseFacade;
import com.example.inventory.control.api.warehouse.WarehousesResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Validated
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    private static final Logger LOGGER = Logger.getLogger(WarehouseController.class.getName());

    private final WarehouseFacade warehouseFacade;

    public WarehouseController(WarehouseFacade warehouseFacade) {
        this.warehouseFacade = warehouseFacade;
    }

    @GetMapping
    public ResponseEntity<WarehousesResponseBody> getAllWarehouses() {
        LOGGER.info("Запрос на получение всех мест хранения.");
        WarehousesResponseBody response = warehouseFacade.getAllWarehouses();
        LOGGER.info(String.format("Запрос на получение всех мест хранения выполнен успешно. Количество: %d.", response.getWarehouses().size()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/remains")
    public ResponseEntity<RemainsResponseBody> getRemainsByWarehouseId(@PathVariable Long id) {
        LOGGER.info(String.format("Запрос на получение остаков ресурсов на складе 'id: %d'.", id));
        RemainsResponseBody responseBody = warehouseFacade.getRemainsByWarehouseId(id);
        if (responseBody.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на получение остатков ресурсов на складе выполнен успешно 'количество: %d'.", responseBody.getRemains().size()));
        } else {
            LOGGER.info(String.format("Запрос на получение остатков ресурсов на складе не выполнен. Причина: %s", responseBody.getDescription()));
        }
        return ResponseEntity.ok(responseBody);
    }

}
