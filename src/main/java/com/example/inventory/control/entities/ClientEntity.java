package com.example.inventory.control.entities;

import com.example.inventory.control.enums.ClientType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Таблица клиентов.
 */
@Entity
@Table(name = "CLIENTS")
public class ClientEntity {

    /**
     * Идентификатор.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Тип.
     */
    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClientType type;

    /**
     * Фамилия.
     */
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    /**
     * Имя.
     */
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    /**
     * Отчество.
     */
    @Column(name = "MIDDLE_NAME")
    private String middleName;

    /**
     * Номер телефона.
     */
    @Column(name = "PHONE")
    private String phone;

    public ClientEntity() {
    }

    public ClientEntity(Long id, ClientType type, String lastName, String firstName, String middleName, String phone) {
        this.id = id;
        this.type = type;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.phone = phone;
    }

    public Long getId() {
        return id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
