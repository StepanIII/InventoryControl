package com.example.inventory.control.domain.models;

import com.example.inventory.control.entities.RoleEntity;
import com.example.inventory.control.utils.CheckParamUtil;

import java.util.Optional;
import java.util.Set;

/**
 * Доменная модель "Пользователь".
 */
public final class User {

    /**
     * Идентификатор.
     */
    private Long id;

    /**
     * Логин.
     */
    private String login;

    /**
     * Пароль.
     */
    private String password;

    /**
     * Электронная почта.
     */
    private String email;

    /**
     * Роли.
     */
    private Set<Role> roles;

    public User(Long id, String login, String password, String email, Set<Role> roles) {
        CheckParamUtil.isNotBlank("login", login);
        CheckParamUtil.isNotBlank("password", password);
        CheckParamUtil.isNotBlank("email", email);
        CheckParamUtil.isNotEmpty("roles", roles);

        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public static User create(String login, String password, String email, Set<Role> roles) {
        return new User(null, login, password, email, roles);
    }

    public Optional<Long> id() {
        return Optional.ofNullable(id);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
