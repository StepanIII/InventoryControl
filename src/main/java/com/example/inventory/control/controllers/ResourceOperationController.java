package com.example.inventory.control.controllers;

import com.example.inventory.control.api.resource.operation.acceptance.AcceptResponseBody;
import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.resource.operation.issue.AllIssueResponseBody;
import com.example.inventory.control.api.resource.operation.issue.IssueRequestBody;
import com.example.inventory.control.api.resource.operation.issue.IssueResponseBody;
import com.example.inventory.control.facades.ResourceOperationFacade;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptRequestBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.acceptance.AllAcceptResponseBody;
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
@RequestMapping("/resource-operation")
public class ResourceOperationController {

    private static final Logger LOGGER = Logger.getLogger(ResourceOperationController.class.getName());

    private final ResourceOperationFacade resourceOperationFacade;

    public ResourceOperationController(ResourceOperationFacade resourceOperationFacade) {
        this.resourceOperationFacade = resourceOperationFacade;
    }

    @GetMapping("/accept")
    public ResponseEntity<AllAcceptResponseBody> getAllAcceptance() {
        LOGGER.info("Запрос на получение всех приемок ресрусов.");
        AllAcceptResponseBody responseBody = resourceOperationFacade.getAllAccept();
        LOGGER.info(String.format(
                "Запрос на получение всех приемок ресурсов выполнен успешно. Количество %d.",
                responseBody.getAcceptance().size()));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/accept/{id}")
    public ResponseEntity<AcceptResponseBody> getAcceptById(@PathVariable Long id) {
        LOGGER.info(String.format("Запрос на получение приемки ресурсов 'id: %s'.", id));
        AcceptResponseBody responseBody = resourceOperationFacade.getAcceptById(id);
        if (responseBody.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format(
                    "Запрос на получение приемки ресурсов 'id: %d' выполнен успешно.",
                    responseBody.getAccept().getId()));
        } else {
            LOGGER.info(String.format(
                    "Запрос на получение приемки ресурсов не выполнен. Причина: %s",
                    responseBody.getDescription()));
        }
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/accept")
    public ResponseEntity<BaseResponseBody> addAccept(@Valid @RequestBody AcceptRequestBody request) {
        LOGGER.info("Запрос на добавление приемки ресурсов.");
        BaseResponseBody responseBody = resourceOperationFacade.addAccept(request);
        if (responseBody.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info("Запрос на добавление приемки ресурсов выполнен успешно.");
        } else {
            LOGGER.info(String.format("Запрос на добавление приемки ресурсов не выполнен. Причина: %s",
                    responseBody.getDescription()));
        }
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/issue")
    public ResponseEntity<AllIssueResponseBody> getAllIssue() {
        LOGGER.info("Запрос на получение всех выдач ресрусов.");
        AllIssueResponseBody responseBody = resourceOperationFacade.getAllIssue();
        LOGGER.info(String.format(
                "Запрос на получение всех выдач ресурсов выполнен успешно. Количество %d.",
                responseBody.getIssuance().size()));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/issue/{id}")
    public ResponseEntity<IssueResponseBody> getIssueById(@PathVariable Long id) {
        LOGGER.info(String.format("Запрос на получение выдачи ресурсов 'id: %s'.", id));
        IssueResponseBody responseBody = resourceOperationFacade.getIssueById(id);
        if (responseBody.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format(
                    "Запрос на получение выдачи ресурсов 'id: %d' выполнен успешно.",
                    responseBody.getIssue().getId()));
        } else {
            LOGGER.info(String.format(
                    "Запрос на получение выдачи ресурсов не выполнен. Причина: %s",
                    responseBody.getDescription()));
        }
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/issue")
    public ResponseEntity<BaseResponseBody> addAccept(@Valid @RequestBody IssueRequestBody request) {
        LOGGER.info("Запрос на добавление выдачи ресурсов.");
        BaseResponseBody responseBody = resourceOperationFacade.addIssue(request);
        if (responseBody.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info("Запрос на добавление выдачи ресурсов выполнен успешно.");
        } else {
            LOGGER.info(String.format("Запрос на добавление выдачи ресурсов не выполнен. Причина: %s",
                    responseBody.getDescription()));
        }
        return ResponseEntity.ok(responseBody);
    }

}
