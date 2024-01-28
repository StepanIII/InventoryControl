package com.example.inventory.control.controllers;

import com.example.inventory.control.facades.WriteOffFacade;
import com.example.inventory.control.ui.models.requests.acceptance.AddAcceptRequest;
import com.example.inventory.control.ui.models.requests.acceptance.UpdateAcceptRequest;
import com.example.inventory.control.ui.models.requests.writeOff.UpdateWriteOffRequest;
import com.example.inventory.control.ui.models.responses.StatusResponse;
import com.example.inventory.control.ui.models.responses.acceptance.AcceptResourcesResponse;
import com.example.inventory.control.ui.models.responses.acceptance.AddAcceptResponse;
import com.example.inventory.control.ui.models.responses.acceptance.UpdateAcceptResponse;
import com.example.inventory.control.ui.models.responses.remaining.RemainingResponse;
import com.example.inventory.control.ui.models.responses.writeoff.AddWriteOffRequest;
import com.example.inventory.control.ui.models.responses.writeoff.AddWriteOffResponse;
import com.example.inventory.control.ui.models.responses.writeoff.UpdateWriteOffResponse;
import com.example.inventory.control.ui.models.responses.writeoff.WriteOffResourceCountResponse;
import com.example.inventory.control.ui.models.responses.writeoff.WriteOffResourcesResponse;
import com.example.inventory.control.ui.models.responses.writeoff.WriteOffResponse;
import com.example.inventory.control.ui.models.responses.writeoff.WriteOffsResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/write-off")
public class WriteOffController {

    private static final Logger LOGGER = Logger.getLogger(WriteOffController.class.getName());

    private final WriteOffFacade writeOffFacade;

    public WriteOffController(WriteOffFacade writeOffFacade) {
        this.writeOffFacade = writeOffFacade;
    }

    @GetMapping
    public ResponseEntity<WriteOffsResponse> getAllWriteOff() {
        LOGGER.info("Запрос на получение всех списаний.");
        WriteOffsResponse response = writeOffFacade.getListAllWriteOff();
        LOGGER.info(String.format("Запрос на получение всех списаний выполнен успешно. Количество: %d.", response.getWriteOffs().size()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WriteOffResourcesResponse> getWriteOffById(@Valid @PathVariable Long id) {
        LOGGER.info(String.format("Запрос на получение списания 'id: %s'.", id));
        WriteOffResourcesResponse response = writeOffFacade.getWriteOffById(id);
        if (response.getStatusResponse() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на получение списания 'id: %d' выполнен успешно.", response.getId()));
        } else {
            LOGGER.info(String.format("Запрос на получение списания не выполнен. Причина: %s", response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AddWriteOffResponse> addWriteOff(@Valid @RequestBody AddWriteOffRequest request) {
        LOGGER.info("Запрос на добавление списания.");
        AddWriteOffResponse response = writeOffFacade.addWriteOff(request);
        if (response.getStatusResponse() == StatusResponse.SUCCESS) {
            LOGGER.info("Запрос на добавление списания 'id: %d' выполнен успешно.");
        } else {
            LOGGER.info(String.format("Запрос на добавление приемки не выполнен. Причина: %s",
                    response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateWriteOffResponse> updateWriteOff(@Valid @NotNull @PathVariable Long id,
                                                                 @Valid @RequestBody UpdateWriteOffRequest request) {
        LOGGER.info(String.format("Запрос на обновление списания 'id: %d'.", id));
        UpdateWriteOffResponse response = writeOffFacade.updateWriteOff(id, request);
        if (response.getStatusResponse() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на обновление списания 'id: %d' выполнен успешно.",
                    id));
        } else {
            LOGGER.info(String.format("Запрос на обновление списания не выполнен 'id: %d'. Причина: %s",
                    id, response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

}
