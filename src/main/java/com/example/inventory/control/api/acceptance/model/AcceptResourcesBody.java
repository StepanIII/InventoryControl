package com.example.inventory.control.api.acceptance.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Тело ответа "Приемка с ресурсами".
 */
public class AcceptResourcesBody {

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
    private List<ResourceCountBody> resources;

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

    public List<ResourceCountBody> getResources() {
        return resources;
    }

    public void setResources(List<ResourceCountBody> resources) {
        this.resources = resources;
    }
}
