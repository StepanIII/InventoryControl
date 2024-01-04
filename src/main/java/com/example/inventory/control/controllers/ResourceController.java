package com.example.inventory.control.controllers;

import com.example.inventory.control.facades.ResourceFacade;
import com.example.inventory.control.models.requests.AddResourceRequest;
import com.example.inventory.control.models.requests.UpdateResourceRequest;
import com.example.inventory.control.models.responses.AddResourceResponse;
import com.example.inventory.control.models.responses.DeleteResourceResponse;
import com.example.inventory.control.models.responses.ResourcesResponse;
import com.example.inventory.control.models.responses.StatusResponse;
import com.example.inventory.control.models.responses.UpdateResourceResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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

    @PostMapping
    public ResponseEntity<AddResourceResponse> addResource(@Valid @RequestBody AddResourceRequest request) {
        LOGGER.info(String.format("Запрос на добавление ресурса 'name: %s'.", request.getName()));
        AddResourceResponse response = resourceFacade.addResource(request);
        if (response.getStatusResponse() == StatusResponse.SUCCESS)  {
            LOGGER.info(String.format("Запрос на добавление ресурса 'name: %s, id: %d' выполнен успешно.",
                    response.getAddedResource().getName(), response.getAddedResource().getId()));
        } else {
            LOGGER.info(String.format("Запрос на добавление ресурса 'name: %s' не выполнен. Причина: %s",
                    request.getName(), response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateResourceResponse> updateResource(@NotNull @PathVariable Long id,
                                                                 @Valid @RequestBody UpdateResourceRequest request) {
        LOGGER.info(String.format("Запрос на обновление ресурса 'id: %d'.", id));
        UpdateResourceResponse response = resourceFacade.updateResource(id, request);
        if (response.getStatusResponse() == StatusResponse.SUCCESS)  {
            LOGGER.info(String.format("Запрос на добавление ресурса 'id: %d' выполнен успешно.", id));
        } else {
            LOGGER.info(String.format("Запрос на добавление ресурса 'id: %d' не выполнен. Причина: %s",
                    id, response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ResourcesResponse> getAllResources() {
        LOGGER.info("Запрос на получение всех ресурсов.");
        ResourcesResponse resourcesResponse = resourceFacade.getListAllResources();
        LOGGER.info("Запрос на получение всех ресурсов выполнен успешно.");
        return ResponseEntity.ok(resourcesResponse);
    }

    // @NotNull нужна?
    // Переделать на delete
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResourceResponse> deleteResource(@NotNull @PathVariable Long id) {
        LOGGER.info(String.format("Запрос на удаление ресурса 'id: %d'.", id));
        DeleteResourceResponse response = resourceFacade.deleteResource(id);
        if (response.getStatusResponse() == StatusResponse.SUCCESS)  {
            LOGGER.info(String.format("Запрос на удаление ресурса выполнен успешно 'id: %d'.", id));
        } else {
            LOGGER.info(String.format("Запрос на удаление ресурса не выполнен 'id: %d. " +
                    "Причина: %s", id, response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }
}
