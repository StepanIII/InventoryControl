package com.example.inventory.control.controllers;

import com.example.inventory.control.api.acceptance.AcceptResourcesResponse;
import com.example.inventory.control.api.responses.BaseResponse;
import com.example.inventory.control.facades.AcceptFacade;
import com.example.inventory.control.api.acceptance.AcceptRequest;
import com.example.inventory.control.api.responses.StatusResponse;
import com.example.inventory.control.api.acceptance.AcceptanceResponse;
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
@RequestMapping("/accept")
public class AcceptController {

    private static final Logger LOGGER = Logger.getLogger(AcceptController.class.getName());

    private final AcceptFacade acceptFacade;

    public AcceptController(AcceptFacade acceptFacade) {
        this.acceptFacade = acceptFacade;
    }

    @GetMapping
    public ResponseEntity<AcceptanceResponse> getAllAcceptance() {
        LOGGER.info("Запрос на получение всех приемок.");
        AcceptanceResponse response = acceptFacade.getAllAcceptance();
        LOGGER.info(String.format("Запрос на получение всех приемок выполнен успешно. Количество %d.", response.getAcceptance().size()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcceptResourcesResponse> getAcceptById(@Valid @PathVariable Long id) {
        LOGGER.info(String.format("Запрос на получение приемки 'id: %s'.", id));
        AcceptResourcesResponse response = acceptFacade.getAcceptById(id);
        if (response.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на получение приемки 'id: %d' выполнен успешно.", response.getAccept().getId()));
        } else {
            LOGGER.info(String.format("Запрос на получение приемки не выполнен. Причина: %s", response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponse> addAccept(@Valid @RequestBody AcceptRequest request) {
        LOGGER.info("Запрос на добавление приемки.");
        BaseResponse response = acceptFacade.addAccept(request);
        if (response.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info("Запрос на добавление приемки выполнен успешно.");
        } else {
            LOGGER.info(String.format("Запрос на добавление приемки не выполнен. Причина: %s",
                    response.getDescription()));
        }
        return ResponseEntity.ok(response);

    }

//    @PutMapping("/{id}")
//    public ResponseEntity<BaseResponse> updateAccept(@Valid @NotNull @PathVariable Long id,
//                                                             @Valid @RequestBody AcceptRequest request) {
//        LOGGER.info(String.format("Запрос на обновление приемки 'id: %d'.", id));
//        BaseResponse response = acceptFacade.updateAccept(id, request);
//        if (response.getStatus() == StatusResponse.SUCCESS) {
//            LOGGER.info(String.format("Запрос на обновление приемки 'id: %d' выполнен успешно.",
//                    id));
//        } else {
//            LOGGER.info(String.format("Запрос на обновление приемки не выполнен 'id: %d'. Причина: %s",
//                    id, response.getDescription()));
//        }
//        return ResponseEntity.ok(response);
//    }

}
