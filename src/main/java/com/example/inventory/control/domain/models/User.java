package com.example.inventory.control.domain.models;

import com.example.inventory.control.utils.BCryptUtil;
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
     * Фамилия.
     */
    private String lastName;

    /**
     * Имя.
     */
    private String firstName;

    /**
     * Отчество.
     */
    private String middleName;

    /**
     * Электронная почта.
     */
    private String email;

    /**
     * Роли.
     */
    private Set<Role> roles;

    public User(Long id, String login, String password, String lastName, String firstName, String middleName, String email, Set<Role> roles) {
        CheckParamUtil.isNotBlank("login", login);
        CheckParamUtil.isNotBlank("password", password);
        CheckParamUtil.isNotBlank("lastName", lastName);
        CheckParamUtil.isNotBlank("firstName", firstName);
        CheckParamUtil.isNotBlank("email", email);
        CheckParamUtil.isNotEmpty("roles", roles);

        this.id = id;
        this.login = login;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.email = email;
        this.roles = roles;
    }

    public static User create(String login, String password, String lastName, String firstName, String middleName, String email, Set<Role> roles) {
        return new User(null, login, BCryptUtil.encode(password), lastName, firstName, middleName, email, roles);
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

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Optional<String> middleName() {
        return Optional.ofNullable(middleName);
    }

    public String getEmail() {
        return email;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
