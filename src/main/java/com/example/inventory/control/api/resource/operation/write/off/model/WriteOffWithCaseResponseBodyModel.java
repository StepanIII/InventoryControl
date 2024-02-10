package com.example.inventory.control.api.resource.operation.write.off.model;

import com.example.inventory.control.api.resource.operation.ResourceCountResponseBodyModel;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Модель для тела ответа "Списание".
 */
public class WriteOffWithCaseResponseBodyModel {

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
     * Наименование склада.
     */
    private String warehouseName;

    /**
     * Причина.
     */
    private String description;

    /**
     * Списанные ресурсы.
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ResourceCountResponseBodyModel> getResources() {
        return resources;
    }

    public void setResources(List<ResourceCountResponseBodyModel> resources) {
        this.resources = resources;
    }
}
