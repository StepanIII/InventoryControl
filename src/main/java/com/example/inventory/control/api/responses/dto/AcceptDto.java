package com.example.inventory.control.api.responses.dto;

import java.time.LocalDateTime;

/**
 * DTO для сущности "Приемка".
 */
public class AcceptDto {

    /**
     * Идентификатор.
     */
    private Long id;

    /**
     * Время создания.
     */
    private LocalDateTime createdTime;

    /**
     * Наименование места хранения.
     */
    private String warehouseName;

    /**
     * ФИО благодетеля.
     */
    private String benefactorFio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getBenefactorFio() {
        return benefactorFio;
    }

    public void setBenefactorFio(String benefactorFio) {
        this.benefactorFio = benefactorFio;
    }
}
