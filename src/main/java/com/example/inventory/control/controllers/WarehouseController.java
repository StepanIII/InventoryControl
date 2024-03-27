package com.example.inventory.control.controllers;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resources.ResourceRequest;
import com.example.inventory.control.api.resources.ResourceResponseBody;
import com.example.inventory.control.api.warehouse.RemainsResponseBody;
import com.example.inventory.control.api.warehouse.WarehouseRequest;
import com.example.inventory.control.api.warehouse.WarehouseResponseBody;
import com.example.inventory.control.facades.WarehouseFacade;
import com.example.inventory.control.api.warehouse.WarehousesResponseBody;
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

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseResponseBody> getWarehouseById(@PathVariable Long id) {
        LOGGER.info(String.format("Запрос на получение склада по 'id: %d'.", id));
        WarehouseResponseBody response = warehouseFacade.getWarehouseById(id);
        if (response.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на получение склада по 'id: %d' выполнен успешно.", id));
        } else {
            LOGGER.info(String.format("Запрос на получение склада по 'id: %d' не выполнен. Причина: %s.",
                    id, response.getDescription()));
        }
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

    @PostMapping
    public ResponseEntity<BaseResponseBody> addWarehouse(@Valid @RequestBody WarehouseRequest request) {
        LOGGER.info(String.format("Запрос на добавление склада 'name: %s'.", request.getName()));
        BaseResponseBody response = warehouseFacade.addWarehouse(request);
        if (response.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на добавление склада 'name: %s выполнен успешно.", request.getName()));
        } else {
            LOGGER.info(String.format("Запрос на добавление склада 'name: %s' не выполнен. Причина: %s",
                    request.getName(), response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseBody> updateWarehouse(@PathVariable Long id,
                                                            @Valid @RequestBody WarehouseRequest request) {
        LOGGER.info(String.format("Запрос на обновление склада 'id: %d'.", id));
        BaseResponseBody response = warehouseFacade.updateWarehouse(id, request);
        if (response.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на обновление склада 'id: %d' выполнен успешно.", id));
        } else {
            LOGGER.info(String.format("Запрос на обновление склада 'id: %d' не выполнен. Причина: %s",
                    id, response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseBody> deleteWarehouse(@PathVariable Long id) {
        LOGGER.info(String.format("Запрос на удаление склада 'id: %d'.", id));
        BaseResponseBody response = warehouseFacade.deleteWarehouse(id);
        if (response.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на удаление скалада выполнен успешно 'id: %d'.", id));
        } else {
            LOGGER.info(String.format("Запрос на удаление скалада не выполнен 'id: %d. " +
                    "Причина: %s", id, response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

}
