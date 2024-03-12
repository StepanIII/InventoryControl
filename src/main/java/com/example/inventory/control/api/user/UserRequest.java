package com.example.inventory.control.api.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Запрос добавления полльзователя.
 */
public class UserRequest {

    /**
     * Логин.
     */
    @NotBlank(message = "Логин не может быть пустым.")
    @Size(min = 2, message = "Размер логина должен быть минимум 2 символа.")
    private String login;

    /**
     * Пароль.
     */
    @Pattern(regexp = "^(?!.*\\s).{6}$", message = "Пароль должен состоять из 6 символов без пробелов.")
    private String password;

    /**
     * Электронная почта.
     */
    @Email(regexp = "^[\\w.-]+@[a-zA-Z\\d.-]+.[a-zA-Z]{2,}$", message = "Почта не соответствует стандарту.")
    private String email;

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
}
