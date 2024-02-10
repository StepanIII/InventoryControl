package com.example.inventory.control.controllers;

import com.example.inventory.control.api.resource.operation.acceptance.AcceptResponseBody;
import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.resource.operation.capitalization.AllCapitalizationResponseBody;
import com.example.inventory.control.api.resource.operation.capitalization.CapitalizationRequestBody;
import com.example.inventory.control.api.resource.operation.capitalization.CapitalizationResponseBody;
import com.example.inventory.control.api.resource.operation.capitalization.model.CapitalizationWithCaseResponseBodyModel;
import com.example.inventory.control.api.resource.operation.issue.AllIssueResponseBody;
import com.example.inventory.control.api.resource.operation.issue.IssueRequestBody;
import com.example.inventory.control.api.resource.operation.issue.IssueResponseBody;
import com.example.inventory.control.api.resource.operation.write.off.AllWriteOffResponseBody;
import com.example.inventory.control.api.resource.operation.write.off.WriteOffRequestBody;
import com.example.inventory.control.api.resource.operation.write.off.WriteOffResponseBody;
import com.example.inventory.control.api.resource.operation.write.off.model.WriteOffWithCaseResponseBodyModel;
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
    public ResponseEntity<BaseResponseBody> addIssue(@Valid @RequestBody IssueRequestBody request) {
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

    @GetMapping("/capitalization")
    public ResponseEntity<AllCapitalizationResponseBody> getAllCapitalization() {
        LOGGER.info("Запрос на получение всех оприходований ресрусов.");
        AllCapitalizationResponseBody responseBody = resourceOperationFacade.getAllCapitalization();
        LOGGER.info(String.format(
                "Запрос на получение всех оприходований ресурсов выполнен успешно. Количество %d.",
                responseBody.getCapitalization().size()));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/capitalization/{id}")
    public ResponseEntity<CapitalizationResponseBody> getCapitalizationById(@PathVariable Long id) {
        LOGGER.info(String.format("Запрос на получение оприходования ресурсов 'id: %s'.", id));
        CapitalizationResponseBody responseBody = resourceOperationFacade.getCapitalizationById(id);
        if (responseBody.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format(
                    "Запрос на получение оприходования ресурсов 'id: %d' выполнен успешно.",
                    responseBody.getCapitalization().getId()));
        } else {
            LOGGER.info(String.format(
                    "Запрос на получение оприходования ресурсов не выполнен. Причина: %s",
                    responseBody.getDescription()));
        }
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/capitalization")
    public ResponseEntity<BaseResponseBody> addCapitalization(@Valid @RequestBody CapitalizationRequestBody request) {
        LOGGER.info("Запрос на добавление оприходования ресурсов.");
        BaseResponseBody responseBody = resourceOperationFacade.addCapitalization(request);
        if (responseBody.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info("Запрос на добавление оприходования ресурсов выполнен успешно.");
        } else {
            LOGGER.info(String.format("Запрос на добавление оприходования ресурсов не выполнен. Причина: %s",
                    responseBody.getDescription()));
        }
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/write-off")
    public ResponseEntity<AllWriteOffResponseBody> getAllWriteOff() {
        LOGGER.info("Запрос на получение всех списаний ресрусов.");
        AllWriteOffResponseBody responseBody = resourceOperationFacade.getAllWriteOff();
        LOGGER.info(String.format(
                "Запрос на получение всех списаний ресурсов выполнен успешно. Количество %d.",
                responseBody.getWriteOffs().size()));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/write-off/{id}")
    public ResponseEntity<WriteOffResponseBody> getWriteOffById(@PathVariable Long id) {
        LOGGER.info(String.format("Запрос на получение списания ресурсов 'id: %s'.", id));
        WriteOffResponseBody responseBody = resourceOperationFacade.getWriteOffById(id);
        if (responseBody.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format(
                    "Запрос на получение списания ресурсов 'id: %d' выполнен успешно.",
                    responseBody.getWriteOff().getId()));
        } else {
            LOGGER.info(String.format(
                    "Запрос на получение списания ресурсов не выполнен. Причина: %s",
                    responseBody.getDescription()));
        }
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/write-off")
    public ResponseEntity<BaseResponseBody> addIssue(@Valid @RequestBody WriteOffRequestBody request) {
        LOGGER.info("Запрос на добавление списания ресурсов.");
        BaseResponseBody responseBody = resourceOperationFacade.addWriteOff(request);
        if (responseBody.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info("Запрос на добавление списания ресурсов выполнен успешно.");
        } else {
            LOGGER.info(String.format("Запрос на добавление списания ресурсов не выполнен. Причина: %s",
                    responseBody.getDescription()));
        }
        return ResponseEntity.ok(responseBody);
    }

}
