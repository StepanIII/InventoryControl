package com.example.inventory.control.controllers;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptRequestBody;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptResponseBody;
import com.example.inventory.control.api.resource.operation.move.AllMoveResponseBody;
import com.example.inventory.control.api.resource.operation.move.MoveRequestBody;
import com.example.inventory.control.api.resource.operation.move.MoveResponseBody;
import com.example.inventory.control.facades.MoveFacade;
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
@RequestMapping("/move")
public class MoveController {

    private static final Logger LOGGER = Logger.getLogger(MoveController.class.getName());

    private final MoveFacade moveFacade;

    public MoveController(MoveFacade moveFacade) {
        this.moveFacade = moveFacade;
    }

    @GetMapping
    public ResponseEntity<AllMoveResponseBody> getAllMove() {
        LOGGER.info("Запрос на получение всех еремещений ресрусов.");
        AllMoveResponseBody responseBody = moveFacade.getAllMove();
        LOGGER.info(String.format(
                "Запрос на получение всех перемещений ресурсов выполнен успешно. Количество %d.",
                responseBody.getMoves().size()));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoveResponseBody> getMoveById(@PathVariable Long id) {
        LOGGER.info(String.format("Запрос на получение перемещения ресурсов 'id: %s'.", id));
        MoveResponseBody responseBody = moveFacade.getMoveById(id);
        if (responseBody.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format(
                    "Запрос на получение приемки ресурсов 'id: %d' выполнен успешно.",
                    responseBody.getMove().getId()));
        } else {
            LOGGER.info(String.format(
                    "Запрос на получение приемки ресурсов не выполнен. Причина: %s",
                    responseBody.getDescription()));
        }
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping
    public ResponseEntity<BaseResponseBody> addMove(@Valid @RequestBody MoveRequestBody request) {
        LOGGER.info("Запрос на добавление перемещения ресурсов.");
        BaseResponseBody responseBody = moveFacade.addMove(request);
        if (responseBody.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info("Запрос на добавление перемещения ресурсов выполнен успешно.");
        } else {
            LOGGER.info(String.format("Запрос на добавление перемещения ресурсов не выполнен. Причина: %s",
                    responseBody.getDescription()));
        }
        return ResponseEntity.ok(responseBody);
    }

}
