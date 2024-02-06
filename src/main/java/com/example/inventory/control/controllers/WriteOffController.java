package com.example.inventory.control.controllers;

import com.example.inventory.control.api.BaseResponse;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.writeoff.WriteOffRequest;
import com.example.inventory.control.api.writeoff.WriteOffResourcesResponse;
import com.example.inventory.control.api.writeoff.WriteOffsResponse;
import com.example.inventory.control.facades.WriteOffFacade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    public ResponseEntity<WriteOffResourcesResponse> getWriteOffById(@Valid @NotNull @PathVariable Long id) {
        LOGGER.info(String.format("Запрос на получение списания 'id: %s'.", id));
        WriteOffResourcesResponse response = writeOffFacade.getWriteOffById(id);
        if (response.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на получение списания 'id: %d' выполнен успешно.", response.getWriteOffResources().getId()));
        } else {
            LOGGER.info(String.format("Запрос на получение списания не выполнен. Причина: %s", response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponse> addWriteOff(@Valid @RequestBody WriteOffRequest request) {
        LOGGER.info("Запрос на добавление списания.");
        BaseResponse response = writeOffFacade.addWriteOff(request);
        if (response.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info("Запрос на добавление списания выполнен успешно.");
        } else {
            LOGGER.info(String.format("Запрос на добавление списания не выполнен. Причина: %s",
                    response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<UpdateWriteOffResponse> updateWriteOff(@Valid @NotNull @PathVariable Long id,
//                                                                 @Valid @RequestBody UpdateWriteOffRequest request) {
//        LOGGER.info(String.format("Запрос на обновление списания 'id: %d'.", id));
//        UpdateWriteOffResponse response = writeOffFacade.updateWriteOff(id, request);
//        if (response.getStatus() == StatusResponse.SUCCESS) {
//            LOGGER.info(String.format("Запрос на обновление списания 'id: %d' выполнен успешно.",
//                    id));
//        } else {
//            LOGGER.info(String.format("Запрос на обновление списания не выполнен 'id: %d'. Причина: %s",
//                    id, response.getDescription()));
//        }
//        return ResponseEntity.ok(response);
//    }

}
