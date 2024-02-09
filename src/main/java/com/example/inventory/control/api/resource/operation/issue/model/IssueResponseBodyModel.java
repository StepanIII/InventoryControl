package com.example.inventory.control.api.resource.operation.issue.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * Модель для тела ответа "Выдача".
 */
public class IssueResponseBodyModel {

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
     * ФИО благополучаетля.
     */
    private String beneficiaryFio;

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

    public String getBeneficiaryFio() {
        return beneficiaryFio;
    }

    public void setBeneficiaryFio(String beneficiaryFio) {
        this.beneficiaryFio = beneficiaryFio;
    }
}
