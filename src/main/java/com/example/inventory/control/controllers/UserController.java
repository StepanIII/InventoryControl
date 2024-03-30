package com.example.inventory.control.controllers;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resources.ResourceRequest;
import com.example.inventory.control.api.resources.ResourceResponseBody;
import com.example.inventory.control.api.resources.ResourcesResponseBody;
import com.example.inventory.control.api.user.UserAllRolesResponseBody;
import com.example.inventory.control.api.user.UserCreateAdminRequest;
import com.example.inventory.control.api.user.UserRequest;
import com.example.inventory.control.api.user.UserResponseBody;
import com.example.inventory.control.api.user.UserWithRolesResponseBody;
import com.example.inventory.control.api.user.UsersResponseBody;
import com.example.inventory.control.facades.UserFacade;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.logging.Logger;

@Controller
@RequestMapping("/user")
// Сделать отдельно контроллер регистрации
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

    @PostMapping("/add")
    public ResponseEntity<BaseResponseBody> addUser(@Valid @RequestBody UserCreateAdminRequest userRequest) {
        LOGGER.info("Запрос на добавление пользователя.");
        BaseResponseBody responseBody = userFacade.addUser(userRequest);
        if (responseBody.getStatus() != StatusResponse.SUCCESS) {
            LOGGER.info(String.format(
                    "Запрос на добавление пользователя не выполнен. Причина: %s",
                    responseBody.getDescription()
            ));
        } else {
            LOGGER.info("Запрос на добавление пользователя выполнен успешно.");
        }
        return ResponseEntity.ok(responseBody);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseBody> updateUser(@PathVariable Long id,
                                                           @Valid @RequestBody UserCreateAdminRequest request) {
        LOGGER.info(String.format("Запрос на обновление пользователя 'id: %d'.", id));
        BaseResponseBody response = userFacade.updateUser(id, request);
        if (response.getStatus() == StatusResponse.SUCCESS) {
            LOGGER.info(String.format("Запрос на обновление пользователя 'id: %d' выполнен успешно.", id));
        } else {
            LOGGER.info(String.format("Запрос на обновление пользователя 'id: %d' не выполнен. Причина: %s",
                    id, response.getDescription()));
        }
        return ResponseEntity.ok(response);
    }

    // Порефакторить метод на получение фамилии и имени
    @GetMapping("/current")
    public ResponseEntity<UserWithRolesResponseBody> getCurrentUserInfo(Principal principal) {
        LOGGER.info("Запрос на получение информации о текущем пользователе.");
        UserWithRolesResponseBody responseBody = userFacade.getUserByLogin(principal.getName());
        if (responseBody.getStatus() != StatusResponse.SUCCESS) {
            LOGGER.info(String.format(
                    "Запрос на получение информации о текущем пользователе не выполнен. Причина: %s",
                    responseBody.getDescription()
            ));
        } else {
            LOGGER.info("Запрос на получение информации о текущем пользователе выполнен успешно.");
        }
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserWithRolesResponseBody> getUserById(@PathVariable Long id) {
        LOGGER.info(String.format("Запрос на получение пользователя по идентификатору: %d.", id));
        UserWithRolesResponseBody responseBody = userFacade.getUserById(id);
        if (responseBody.getStatus() != StatusResponse.SUCCESS) {
            LOGGER.info(String.format(
                    "Запрос на получение пользователя по идентификатору: %d. Причина: %s",
                    id,
                    responseBody.getDescription()
            ));
        } else {
            LOGGER.info(String.format("Запрос на получение пользователя по идентификатору: %d выполнен успешно.", id));
        }
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping
    public ResponseEntity<UsersResponseBody> getAllUsers() {
        LOGGER.info("Запрос на получение всех клиентов.");
        UsersResponseBody response = userFacade.getAllUsers();
        LOGGER.info(String.format("Запрос на получение всех клиентов выполнен успешно. Количество: %d.", response.getUsers().size()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/roles")
    public ResponseEntity<UserAllRolesResponseBody> getAllUserRoles() {
        LOGGER.info("Запрос на получение всех ролей пользователей.");
        UserAllRolesResponseBody response = userFacade.getAllUserRoles();
        LOGGER.info(String.format("Запрос на получение всех ролей пользователей выполнен успешно. Количество: %d.", response.getRoles().size()));
        return ResponseEntity.ok(response);
    }

}
