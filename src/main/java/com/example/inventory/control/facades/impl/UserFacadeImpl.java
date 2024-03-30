package com.example.inventory.control.facades.impl;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.user.UserAllRolesResponseBody;
import com.example.inventory.control.api.user.UserCreateAdminRequest;
import com.example.inventory.control.api.user.UserRequest;
import com.example.inventory.control.api.user.UserResponseBody;
import com.example.inventory.control.api.user.UserWithRolesResponseBody;
import com.example.inventory.control.api.user.UsersResponseBody;
import com.example.inventory.control.api.user.model.UserAllInfoModel;
import com.example.inventory.control.api.user.model.UserModel;
import com.example.inventory.control.api.user.model.UserWithRolesModel;
import com.example.inventory.control.domain.models.Role;
import com.example.inventory.control.domain.models.User;
import com.example.inventory.control.facades.UserFacade;
import com.example.inventory.control.mapper.UserMapper;
import com.example.inventory.control.services.RoleService;
import com.example.inventory.control.services.UserService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;

    public UserFacadeImpl(UserService userService, RoleService roleService, UserMapper userMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.userMapper = userMapper;
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
    public BaseResponseBody addUser(UserCreateAdminRequest request) {
        List<Role> roles = roleService.findAllByNames(request.getRoles());
        if (roles.isEmpty()) {
            BaseResponseBody responseBody = new BaseResponseBody();
            responseBody.setStatus(StatusResponse.ROLE_NOT_FOUND);
            responseBody.setDescription("Роли не найдены. " + request.getRoles());
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
                new HashSet<>(roles));
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
        userModelResponse.setLogin(user.getLogin());
        userModelResponse.setLastFirstName(user.getLastName() + " " + user.getFirstName());

        UserResponseBody responseBody = new UserResponseBody();
        responseBody.setStatus(StatusResponse.SUCCESS);
        responseBody.setDescription(String.format("Найден пользователь с логином: %s.", login));
        responseBody.setUser(userModelResponse);
        return responseBody;
    }

    @Override
    public UsersResponseBody getAllUsers() {
        List<UserAllInfoModel> usersResponse = userService.findAllUsers().stream()
                .map(userMapper::toUserAllInfoModel)
                .toList();
        UsersResponseBody responseBody = new UsersResponseBody();
        responseBody.setStatus(StatusResponse.SUCCESS);
        responseBody.setDescription("Все пользователи получены");
        responseBody.setUsers(usersResponse);
        return responseBody;
    }

    @Override
    public UserAllRolesResponseBody getAllUserRoles() {
        List<String> rolesName = roleService.findAll().stream()
                .map(Role::getName)
                .toList();
        UserAllRolesResponseBody responseBody = new UserAllRolesResponseBody();
        responseBody.setStatus(StatusResponse.SUCCESS);
        responseBody.setDescription("Все роли получены");
        responseBody.setRoles(rolesName);
        return responseBody;
    }

    @Override
    public UserWithRolesResponseBody getUserById(Long id) {
        Optional<User> userCandidate = userService.findById(id);
        if (userCandidate.isEmpty()) {
            UserWithRolesResponseBody responseBody = new UserWithRolesResponseBody();
            responseBody.setStatus(StatusResponse.USER_NOT_FOUND);
            responseBody.setDescription(String.format("Пользователь с идентификаторм: %d не найден.", id));
            return responseBody;
        }
        User user = userCandidate.get();
        UserWithRolesModel userModelResponse = new UserWithRolesModel();
        userModelResponse.setId(user.id().orElseThrow());
        userModelResponse.setLogin(user.getLogin());
        userModelResponse.setLastName(user.getLastName());
        userModelResponse.setFirstName(user.getFirstName());
        userModelResponse.setMiddleName(user.middleName().orElse(null));
        userModelResponse.setEmail(user.getEmail());
        userModelResponse.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .toList());

        UserWithRolesResponseBody responseBody = new UserWithRolesResponseBody();
        responseBody.setStatus(StatusResponse.SUCCESS);
        responseBody.setDescription(String.format("Найден пользователь с идентификатором: %d.", id));
        responseBody.setUser(userModelResponse);
        return responseBody;
    }

    @Override
    public BaseResponseBody updateUser(Long id, UserCreateAdminRequest request) {
        Optional<User> userCandidate = userService.findById(id);
        if (userCandidate.isEmpty()) {
            UserWithRolesResponseBody responseBody = new UserWithRolesResponseBody();
            responseBody.setStatus(StatusResponse.USER_NOT_FOUND);
            responseBody.setDescription(String.format("Пользователь с идентификаторм: %d не найден.", id));
            return responseBody;
        }
        List<Role> roles = roleService.findAllByNames(request.getRoles());
        if (roles.isEmpty()) {
            BaseResponseBody responseBody = new BaseResponseBody();
            responseBody.setStatus(StatusResponse.ROLE_NOT_FOUND);
            responseBody.setDescription("Роли не найдены. " + request.getRoles());
            return responseBody;
        }
        User user = userCandidate.get()
                .update(request.getLogin(), request.getPassword(), request.getLastName(), request.getFirstName(), request.getMiddleName(), request.getEmail(), new HashSet<>(roles));

        user = userService.save(user);
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setStatus(StatusResponse.SUCCESS);
        responseBody.setDescription(String.format("Пользователь обновлен. id: %d.", user.id().orElseThrow()));
        return responseBody;
    }

}
