package com.example.inventory.control.api.resource.operation.acceptance.model;

import com.example.inventory.control.api.resource.operation.ResourceCountResponseBodyModel;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Модель тела ответа "Приемка с ресурсами".
 */
public class AcceptWithResourcesBodyModel {

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
     * Имя места хранения.
     */
    private String warehouseName;

    /**
     * Благодетель ФИО.
     */
    private String benefactorFio;

    /**
     * Добавленные ресурсы.
     */
    private List<ResourceCountResponseBodyModel> resources;

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

    public List<ResourceCountResponseBodyModel> getResources() {
        return resources;
    }

    public void setResources(List<ResourceCountResponseBodyModel> resources) {
        this.resources = resources;
    }
}
