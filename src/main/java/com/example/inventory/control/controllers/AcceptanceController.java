package com.example.inventory.control.controllers;

import com.example.inventory.control.facades.AcceptanceFacade;
import com.example.inventory.control.ui.models.requests.acceptance.AddAcceptRequest;
import com.example.inventory.control.ui.models.responses.StatusResponse;
import com.example.inventory.control.ui.models.responses.acceptance.AcceptanceResponse;
import com.example.inventory.control.ui.models.responses.acceptance.AddAcceptResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Validated
@RestController
@RequestMapping("/acceptance")
public class AcceptanceController {

    private static final Logger LOGGER = Logger.getLogger(AcceptanceController.class.getName());

    private final AcceptanceFacade acceptanceFacade;

    public AcceptanceController(AcceptanceFacade acceptanceFacade) {
        this.acceptanceFacade = acceptanceFacade;
    }

    @GetMapping
    public ResponseEntity<AcceptanceResponse> getAllAcceptance() {
        LOGGER.info("Запрос на получение всех приемок.");
        AcceptanceResponse acceptanceResponse = acceptanceFacade.getListAllAcceptance();
        LOGGER.info("Запрос на получение всех приемок выполнен успешно.");
        return ResponseEntity.ok(acceptanceResponse);
    }

    // Удалить тело ответа?
    @PostMapping
    public ResponseEntity<AddAcceptResponse> addAccept(@Valid @RequestBody AddAcceptRequest request) {
        LOGGER.info("Запрос на добавление приемки.");
        AddAcceptResponse response = acceptanceFacade.addAccept(request);
        if (response.getStatusResponse() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на добавление приемки 'id: %d' выполнен успешно.",
                    response.getAddedAccept().getId()));
        } else {
            LOGGER.info(String.format("Запрос на добавление приемки не выполнен. Причина: %s",
                    response.getDescription()));
        }
        return ResponseEntity.ok(response);

    }

}
