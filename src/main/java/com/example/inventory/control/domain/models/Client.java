package com.example.inventory.control.domain.models;

import com.example.inventory.control.enums.ClientType;
import com.example.inventory.control.utils.CheckParamUtil;

import java.util.Optional;

public final class Client {

    /**
     * Идентификатор.
     */
    private Long id;

    /**
     * Тип.
     */
    private ClientType type;

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
     * Телефон.
     */
    private String phone;

    public Client(Long id, ClientType type, String lastName, String firstName, String middleName, String phone) {
        CheckParamUtil.isNotNull("type",type);
        CheckParamUtil.isNotBlank("lastName", lastName);
        CheckParamUtil.isNotBlank("firstName", firstName);

        this.id = id;
        this.type = type;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.phone = phone;
    }

    public Optional<Long> id() {
        return Optional.ofNullable(id);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
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

    public String getFio() {
        if (middleName == null) {
            return lastName + " " + firstName;
        }
        return lastName + " " + firstName + " " + middleName;
    }

    public Optional<String> phone() {
        return Optional.ofNullable(phone);
    }
}
