package com.example.inventory.control.controllers;

import com.example.inventory.control.api.resource.operation.move.AllMoveResponseBody;
import com.example.inventory.control.facades.MoveFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

}
