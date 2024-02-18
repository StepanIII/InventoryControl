package com.example.inventory.control.controllers;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptRequestBody;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptResponseBody;
import com.example.inventory.control.api.resource.operation.acceptance.AllAcceptResponseBody;
import com.example.inventory.control.api.resource.operation.inventory.AllInventoryResponseBody;
import com.example.inventory.control.api.resource.operation.inventory.InventoryResponseBody;
import com.example.inventory.control.facades.InventoryFacade;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

//    @PostMapping("/accept")
//    public ResponseEntity<BaseResponseBody> addAccept(@Valid @RequestBody AcceptRequestBody request) {
//        LOGGER.info("Запрос на добавление приемки ресурсов.");
//        BaseResponseBody responseBody = resourceOperationFacade.addAccept(request);
//        if (responseBody.getStatus() == StatusResponse.SUCCESS) {
//            LOGGER.info("Запрос на добавление приемки ресурсов выполнен успешно.");
//        } else {
//            LOGGER.info(String.format("Запрос на добавление приемки ресурсов не выполнен. Причина: %s",
//                    responseBody.getDescription()));
//        }
//        return ResponseEntity.ok(responseBody);
//    }

//    Изменение
}
