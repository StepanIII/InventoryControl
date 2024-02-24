package com.example.inventory.control.api.resource.operation.inventory;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Тело запроса для добавления инвентаризации.
 */
public class InventoryRequestBody {

    /**
     * Идентификатор места хранения.
     */
    @NotNull(message = "Склад отсутствует")
    private Long warehouseId;

    /**
     * Добавляемые ресурсы.
     */
    @NotNull(message = "Ресурсы не выбраны")
    @Size(min = 1, message = "Ресурсы не выбраны")
    private List<InventoryResourceRequestBody> resources;

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public List<InventoryResourceRequestBody> getResources() {
        return resources;
    }

    public void setResources(List<InventoryResourceRequestBody> resources) {
        this.resources = resources;
    }
}
