package com.example.inventory.control.api.acceptance.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * DTO для сущности "Приемка".
 */
public class AcceptBodyResponse {

    /**
     * Идентификатор.
     */
    private Long id;

    /**
     * Время создания.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
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
