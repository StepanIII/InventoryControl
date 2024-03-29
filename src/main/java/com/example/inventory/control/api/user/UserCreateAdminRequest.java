package com.example.inventory.control.api.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Запрос добавления полльзователя.
 */
public class UserCreateAdminRequest {

    /**
     * Логин.
     */
    @NotBlank(message = "Логин не может быть пустым.")
    @Size(min = 2, message = "Размер логина должен быть минимум 2 символа.")
    private String login;

    /**
     * Пароль.
     */
    @Pattern(regexp = "^(?!.*\\s).{6,}$", message = "Пароль должен состоять минимум из 6 символов без пробелов.")
    private String password;

    /**
     * Фамилия.
     */
    @NotBlank(message = "Укажите фамилию.")
    private String lastName;

    /**
     * Имя.
     */
    @NotBlank(message = "Укажите имя.")
    private String firstName;

    /**
     * Отчество.
     */
    private String middleName;

    /**
     * Электронная почта.
     */
    @Email(regexp = "^[\\w.-]+@[a-zA-Z\\d.-]+.[a-zA-Z]{2,}$", message = "Почта не соответствует стандарту.")
    private String email;

    @Size(min = 1, message = "Укажите роль.")
    private List<String> roles;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
