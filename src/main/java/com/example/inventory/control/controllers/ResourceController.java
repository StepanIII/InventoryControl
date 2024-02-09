package com.example.inventory.control.controllers;

import com.example.inventory.control.api.resources.ResourceRequest;
import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resources.ResourceResponseBody;
import com.example.inventory.control.api.resources.ResourceTypesResponseBody;
import com.example.inventory.control.api.resources.ResourceUnitsResponseBody;
import com.example.inventory.control.api.resources.ResourcesResponseBody;
import com.example.inventory.control.facades.ResourceFacade;
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
@RequestMapping("/resource")
public class ResourceController {

    private static final Logger LOGGER = Logger.getLogger(ResourceController.class.getName());

    private final ResourceFacade resourceFacade;

    public ResourceController(ResourceFacade resourceFacade) {
        this.resourceFacade = resourceFacade;
    }

    @GetMapping
    public ResponseEntity<ResourcesResponseBody> getAllResources() {
        LOGGER.info("Запрос на получение всех ресурсов.");
        ResourcesResponseBody response = resourceFacade.getAllResources();
        LOGGER.info(String.format("Запрос на получение всех ресурсов выполнен успешно. Количество: %d.", response.getResources().size()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceResponseBody> getResourceById(@PathVariable Long id) {
        LOGGER.info(String.format("Запрос на получение ресурса по 'id: %d'.", id));
        ResourceResponseBody response = resourceFacade.getResourceById(id);
        if (response.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на получение ресурса по 'id: %d' выполнен успешно.", id));
        } else {
            LOGGER.info(String.format("Запрос на получение ресурса по 'id: %d' не выполнен. Причина: %s.",
                    id, response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResourceResponseBody> addResource(@Valid @RequestBody ResourceRequest request) {
        LOGGER.info(String.format("Запрос на добавление ресурса 'name: %s'.", request.getName()));
        ResourceResponseBody response = resourceFacade.addResource(request);
        if (response.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на добавление ресурса 'name: %s, id: %d' выполнен успешно.",
                    response.getResource().getName(), response.getResource().getId()));
        } else {
            LOGGER.info(String.format("Запрос на добавление ресурса 'name: %s' не выполнен. Причина: %s",
                    request.getName(), response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceResponseBody> updateResource(@PathVariable Long id,
                                                               @Valid @RequestBody ResourceRequest request) {
        LOGGER.info(String.format("Запрос на обновление ресурса 'id: %d'.", id));
        ResourceResponseBody response = resourceFacade.updateResource(id, request);
        if (response.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на обновление ресурса 'id: %d' выполнен успешно.", id));
        } else {
            LOGGER.info(String.format("Запрос на обновление ресурса 'id: %d' не выполнен. Причина: %s",
                    id, response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseBody> deleteResource(@PathVariable Long id) {
        LOGGER.info(String.format("Запрос на удаление ресурса 'id: %d'.", id));
        BaseResponseBody response = resourceFacade.deleteResource(id);
        if (response.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на удаление ресурса выполнен успешно 'id: %d'.", id));
        } else {
            LOGGER.info(String.format("Запрос на удаление ресурса не выполнен 'id: %d. " +
                    "Причина: %s", id, response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/types")
    public ResponseEntity<ResourceTypesResponseBody> getResourceTypes() {
        LOGGER.info("Запрос на получение всех типов ресурсов.");
        ResourceTypesResponseBody response = resourceFacade.getAllResourceTypes();
        LOGGER.info("Запрос на получение всех типов ресурсов выполнен успешно.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/units")
    public ResponseEntity<ResourceUnitsResponseBody> getResourceUnits() {
        LOGGER.info("Запрос на получение всех единиц измерения ресурсов.");
        ResourceUnitsResponseBody response = resourceFacade.getAllResourceUnits();
        LOGGER.info("Запрос на получение всех единиц измерения ресурсов выполнен успешно.");
        return ResponseEntity.ok(response);
    }
}
