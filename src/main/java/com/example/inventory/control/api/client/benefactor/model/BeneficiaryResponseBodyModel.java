package com.example.inventory.control.api.client.benefactor.model;

/**
 * Модель тела ответа "Благополучатель".
 */
public class BeneficiaryResponseBodyModel {
    /**
     * Идентификатор.
     */
    private Long id;

    /**
     * ФИО.
     */
    private String fio;

    /**
     * Телефон.
     */
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
