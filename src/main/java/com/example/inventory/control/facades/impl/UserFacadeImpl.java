package com.example.inventory.control.facades.impl;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.user.UserRequest;
import com.example.inventory.control.api.user.UserResponseBody;
import com.example.inventory.control.api.user.model.UserModel;
import com.example.inventory.control.domain.models.Role;
import com.example.inventory.control.domain.models.User;
import com.example.inventory.control.facades.UserFacade;
import com.example.inventory.control.services.RoleService;
import com.example.inventory.control.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final RoleService roleService;

    public UserFacadeImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public BaseResponseBody addUser(UserRequest request) {
        Optional<Role> roleUserCandidate = roleService.findByName("ROLE_USER");
        if (roleUserCandidate.isEmpty()) {
            BaseResponseBody responseBody = new BaseResponseBody();
            responseBody.setStatus(StatusResponse.ROLE_NOT_FOUND);
            responseBody.setDescription("Роль с именем: ROLE_USER не найдена.");
            return responseBody;
        }
        if (userService.existsByLogin(request.getLogin())) {
            BaseResponseBody responseBody = new BaseResponseBody();
            responseBody.setStatus(StatusResponse.NOT_VALID_LOGIN);
            responseBody.setDescription("Пользователь с таким логином уже существует.");
            return responseBody;
        }
        if (userService.existsByEmail(request.getEmail())) {
            BaseResponseBody responseBody = new BaseResponseBody();
            responseBody.setStatus(StatusResponse.NOT_VALID_EMAIL);
            responseBody.setDescription("Пользователь с данной электронной почтой уже существует.");
            return responseBody;
        }
        User user = User.create(
                request.getLogin(),
                request.getPassword(),
                request.getLastName(),
                request.getFirstName(),
                request.getMiddleName(),
                request.getEmail(),
                Set.of(roleUserCandidate.get()));
        user = userService.save(user);
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setStatus(StatusResponse.SUCCESS);
        responseBody.setDescription(String.format("Пользователь добавлен. id: %d.", user.id().orElseThrow()));
        return responseBody;
    }

    @Override
    public UserResponseBody getUserByLogin(String login) {
        Optional<User> userCandidate = userService.findByLogin(login);
        if (userCandidate.isEmpty()) {
            UserResponseBody responseBody = new UserResponseBody();
            responseBody.setStatus(StatusResponse.USER_NOT_FOUND);
            responseBody.setDescription(String.format("Пользователь с логином: %s не найден.", login));
            return responseBody;
        }
        User user = userCandidate.get();
        UserModel userModelResponse = new UserModel();
        userModelResponse.setLastFirstName(user.getLastName() + " " + user.getFirstName());

        UserResponseBody responseBody = new UserResponseBody();
        responseBody.setStatus(StatusResponse.SUCCESS);
        responseBody.setDescription(String.format("Найден пользователь с логином: %s.", login));
        responseBody.setUser(userModelResponse);
        return responseBody;
    }

}
