package com.example.inventory.control.api.client.benefactor.model;

/**
 * Модель тела ответа "Благодетель".
 */
public class BenefactorResponseBodyModel {
    /**
     * Идентификатор.
     */
    private Long id;

    /**
     * ФИО.
     */
    private String fio;

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
}
