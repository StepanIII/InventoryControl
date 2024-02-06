package com.example.inventory.control.api.writeoff;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Запрос на добавление списания.
 */
public class WriteOffRequest {

    /**
     * Идентификатор места хранения.
     */
    @NotNull
    private Long warehouseId;

    /**
     * Ресурсы.
     */
    @NotNull
    @Size(min = 1)
    private List<WriteOffResourceCountRequest> resources;

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public List<WriteOffResourceCountRequest> getResources() {
        return resources;
    }

    public void setResources(List<WriteOffResourceCountRequest> resources) {
        this.resources = resources;
    }
}
