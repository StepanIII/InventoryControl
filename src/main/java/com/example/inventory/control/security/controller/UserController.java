package com.example.inventory.control.security.controller;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.user.UserRequest;
import com.example.inventory.control.facades.UserFacade;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @PostMapping
    public ResponseEntity<BaseResponseBody> addUser(@Valid @RequestBody UserRequest request) {
        LOGGER.info("Запрос на добавление пользователя.");
        BaseResponseBody responseBody = userFacade.addUser(request);
        if (responseBody.getStatus() != StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на добавление пользователя не выполенен. Причина: %s.", responseBody.getDescription()));
        }
        return ResponseEntity.ok(responseBody);
    }
}
