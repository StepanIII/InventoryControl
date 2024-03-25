package com.example.inventory.control.controllers;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.user.UserRequest;
import com.example.inventory.control.facades.UserFacade;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequestMapping("/user")
// Переделать на контроллер регистрации
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @PostMapping
    public String addUser(@Valid @ModelAttribute("userRequest") UserRequest userRequest,
                          BindingResult bindingResult,
                          Model model) {
        LOGGER.info("Запрос на добавление пользователя.");

        if (!userRequest.getPassword().equals(userRequest.getPasswordConfirm())) {
            model.addAttribute("error", "Пароли не совпадают.");
            return "registration";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getFieldError().getDefaultMessage());
            return "registration";
        }
        BaseResponseBody responseBody = userFacade.addUser(userRequest);
        if (responseBody.getStatus() != StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на добавление пользователя не выполенен. Причина: %s.", responseBody.getDescription()));
            model.addAttribute("error", responseBody.getDescription());
            return "registration";
        }
        return "login";
    }

    public
}
