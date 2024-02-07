package com.example.inventory.control.controllers;

import com.example.inventory.control.facades.WarehouseFacade;
import com.example.inventory.control.api.warehouse.WarehousesResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<WarehousesResponse> getAllWarehouses() {
        LOGGER.info("Запрос на получение всех мест хранения.");
        WarehousesResponse response = warehouseFacade.getAllWarehouses();
        LOGGER.info(String.format("Запрос на получение всех мест хранения выполнен успешно. Количество: %d.", response.getWarehouses().size()));
        return ResponseEntity.ok(response);
    }

}
