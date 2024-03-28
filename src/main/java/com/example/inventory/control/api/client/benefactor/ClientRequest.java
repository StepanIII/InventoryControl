package com.example.inventory.control.api.client.benefactor;

import com.example.inventory.control.enums.ClientType;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Unit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Запрос добавления клиента.
 */
public class ClientRequest {

    /**
     * Фамилия.
     */
    @NotBlank(message = "Укажите фамилию.")
    @Size(min = 1, max = 255, message = "Количество символов в фамилии должно быть в диапозоне от 1 до 255.")
    private String lastName;

    /**
     * Имя.
     */
    @NotBlank(message = "Укажите имя.")
    @Size(min = 1, max = 255, message = "Количество символов в имени должно быть в диапозоне от 1 до 255.")
    private String firstName;

    /**
     * Отчество.
     */
    private String middleName;

    /**
     * Телефон.
     */
    @Pattern(regexp = "^\\+7\\d{10}$", message = "Проверьте корректность номера телефона.")
    private String phone;

    /**
     * Тип.
     */
    private ClientType type;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
    }
}
