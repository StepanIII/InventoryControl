package com.example.inventory.control.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Сущность таблицы "BENEFACTORS" (Благотворители).
 */
@Entity
@Table(name = "BENEFACTORS")
public class BenefactorEntity {

    /**
     * Идентификатор.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
