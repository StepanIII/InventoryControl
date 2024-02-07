package com.example.inventory.control.controllers;

import com.example.inventory.control.api.resources.ResourceRequest;
import com.example.inventory.control.api.BaseResponse;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resources.ResourceResponse;
import com.example.inventory.control.api.resources.ResourceTypesResponse;
import com.example.inventory.control.api.resources.ResourceUnitsResponse;
import com.example.inventory.control.api.resources.ResourcesResponse;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.facades.ResourceFacade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

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
    public ResponseEntity<ResourcesResponse> getAllResources() {
        LOGGER.info("Запрос на получение всех ресурсов.");
        ResourcesResponse response = resourceFacade.getAllResources();
        LOGGER.info(String.format("Запрос на получение всех ресурсов выполнен успешно. Количество: %d.", response.getResources().size()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceResponse> getResourceById(@PathVariable Long id) {
        LOGGER.info(String.format("Запрос на получение ресурса по 'id: %d'.", id));
        ResourceResponse response = resourceFacade.getResourceById(id);
        if (response.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на получение ресурса по 'id: %d' выполнен успешно.", id));
        } else {
            LOGGER.info(String.format("Запрос на получение ресурса по 'id: %d' не выполнен. Причина: %s.",
                    id, response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResourceResponse> addResource(@Valid @RequestBody ResourceRequest request) {
        LOGGER.info(String.format("Запрос на добавление ресурса 'name: %s'.", request.getName()));
        ResourceResponse response = resourceFacade.addResource(request);
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
    public ResponseEntity<ResourceResponse> updateResource(@PathVariable Long id,
                                                           @Valid @RequestBody ResourceRequest request) {
        LOGGER.info(String.format("Запрос на обновление ресурса 'id: %d'.", id));
        ResourceResponse response = resourceFacade.updateResource(id, request);
        if (response.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на обновление ресурса 'id: %d' выполнен успешно.", id));
        } else {
            LOGGER.info(String.format("Запрос на обновление ресурса 'id: %d' не выполнен. Причина: %s",
                    id, response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteResource(@PathVariable Long id) {
        LOGGER.info(String.format("Запрос на удаление ресурса 'id: %d'.", id));
        BaseResponse response = resourceFacade.deleteResource(id);
        if (response.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на удаление ресурса выполнен успешно 'id: %d'.", id));
        } else {
            LOGGER.info(String.format("Запрос на удаление ресурса не выполнен 'id: %d. " +
                    "Причина: %s", id, response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/types")
    public ResponseEntity<ResourceTypesResponse> getResourceTypes() {
        LOGGER.info("Запрос на получение всех типов ресурсов.");
        ResourceTypesResponse response = resourceFacade.getAllResourceTypes();
        LOGGER.info("Запрос на получение всех типов ресурсов выполнен успешно.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/units")
    public ResponseEntity<ResourceUnitsResponse> getResourceUnits() {
        LOGGER.info("Запрос на получение всех единиц измерения ресурсов.");
        ResourceUnitsResponse response = resourceFacade.getAllResourceUnits();
        LOGGER.info("Запрос на получение всех единиц измерения ресурсов выполнен успешно.");
        return ResponseEntity.ok(response);
    }
}
