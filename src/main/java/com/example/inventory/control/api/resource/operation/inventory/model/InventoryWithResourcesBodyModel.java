package com.example.inventory.control.api.resource.operation.inventory.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Модель тела ответа "Ивентаризация с ресурсами".
 */
public class InventoryWithResourcesBodyModel {

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
     * Идентификтор места хранения.
     */
    private Long warehouseId;

    /**
     * Добавленные ресурсы.
     */
    private List<InventoryResourceResponseBodyModel> resources;

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

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public List<InventoryResourceResponseBodyModel> getResources() {
        return resources;
    }

    public void setResources(List<InventoryResourceResponseBodyModel> resources) {
        this.resources = resources;
    }
}
