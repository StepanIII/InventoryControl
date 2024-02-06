package com.example.inventory.control.api.benefactor.model;

/**
 * Тело ответа "Благодетель".
 */
public class BenefactorBody {
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
