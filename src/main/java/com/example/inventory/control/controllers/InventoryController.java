package com.example.inventory.control.controllers;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptRequestBody;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptResponseBody;
import com.example.inventory.control.api.resource.operation.acceptance.AllAcceptResponseBody;
import com.example.inventory.control.api.resource.operation.inventory.AllInventoryResponseBody;
import com.example.inventory.control.api.resource.operation.inventory.InventoryRequestBody;
import com.example.inventory.control.api.resource.operation.inventory.InventoryResponseBody;
import com.example.inventory.control.facades.InventoryFacade;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Validated
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private static final Logger LOGGER = Logger.getLogger(InventoryController.class.getName());

    private final InventoryFacade inventoryFacade;

    public InventoryController(InventoryFacade inventoryFacade) {
        this.inventoryFacade = inventoryFacade;
    }

    @GetMapping
    public ResponseEntity<AllInventoryResponseBody> getAllInventory() {
        LOGGER.info("Запрос на получение всех инвентаризаций.");
        AllInventoryResponseBody responseBody = inventoryFacade.getAllInventory();
        LOGGER.info(String.format(
                "Запрос на получение всех инвентаризаций выполнен успешно. Количество %d.",
                responseBody.getInventory().size()));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponseBody> getInventoryById(@PathVariable Long id) {
        LOGGER.info(String.format("Запрос на получение инвентаризации 'id: %s'.", id));
        InventoryResponseBody responseBody = inventoryFacade.getInventoryById(id);
        if (responseBody.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format(
                    "Запрос на получение ивентаризации 'id: %d' выполнен успешно.",
                    responseBody.getInventory().getId()));
        } else {
            LOGGER.info(String.format(
                    "Запрос на получение инвентаризации не выполнен. Причина: %s",
                    responseBody.getDescription()));
        }
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping
    public ResponseEntity<BaseResponseBody> addInventory(@Valid @RequestBody InventoryRequestBody request) {
        LOGGER.info("Запрос на добавление инвенатризации ресурсов.");
        BaseResponseBody responseBody = inventoryFacade.addInventory(request);
        if (responseBody.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info("Запрос на добавление инвентаризации ресурсов выполнен успешно.");
        } else {
            LOGGER.info(String.format("Запрос на добавление инвентаризации ресурсов не выполнен. Причина: %s",
                    responseBody.getDescription()));
        }
        return ResponseEntity.ok(responseBody);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseBody> editInventory(@PathVariable Long id, @Valid @RequestBody InventoryRequestBody request) {
        LOGGER.info("Запрос на изменение инвенатризации ресурсов.");
        BaseResponseBody responseBody = inventoryFacade.editInventory(id, request);
        if (responseBody.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info("Запрос на изменение инвентаризации ресурсов выполнен успешно.");
        } else {
            LOGGER.info(String.format("Запрос на изменение инвентаризации ресурсов не выполнен. Причина: %s",
                    responseBody.getDescription()));
        }
        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseBody> deleteInventory(@PathVariable Long id) {
        LOGGER.info("Запрос на удаление инвенатризации ресурсов.");
        BaseResponseBody responseBody = inventoryFacade.deleteById(id);
        if (responseBody.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info("Запрос на удаление инвентаризации ресурсов выполнен успешно.");
        } else {
            LOGGER.info(String.format("Запрос на удаление инвентаризации ресурсов не выполнен. Причина: %s",
                    responseBody.getDescription()));
        }
        return ResponseEntity.ok(responseBody);
    }


}
