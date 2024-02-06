package com.example.inventory.control.api.writeoff.model;

import com.example.inventory.control.api.warehouse.model.WarehouseBody;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 */
public class WriteOffResourcesBody {

    /**
     * Идентификатор.
     */
    private Long id;

    /**
     * Время создания.
     */
    private LocalDateTime createdTime;

    /**
     * Место хранения.
     */
    private WarehouseBody warehouse;

    /**
     * Списанные ресурсы.
     */
    private List<WriteOffResourceCountBody> resources;

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

    public WarehouseBody getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseBody warehouse) {
        this.warehouse = warehouse;
    }

    public List<WriteOffResourceCountBody> getResources() {
        return resources;
    }

    public void setResources(List<WriteOffResourceCountBody> resources) {
        this.resources = resources;
    }
}
