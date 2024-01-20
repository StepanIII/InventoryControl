package com.example.inventory.control.controllers;

import com.example.inventory.control.facades.WriteOffFacade;
import com.example.inventory.control.ui.models.responses.remaining.RemainingResponse;
import com.example.inventory.control.ui.models.responses.writeoff.WriteOffResponse;
import com.example.inventory.control.ui.models.responses.writeoff.WriteOffsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

}
