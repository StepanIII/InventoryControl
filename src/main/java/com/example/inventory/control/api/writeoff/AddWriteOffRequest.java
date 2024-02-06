package com.example.inventory.control.api.writeoff;

import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Модель запроса для добавления списания.
 */
public class AddWriteOffRequest {

    @NotNull
    private Long warehouseId;

    // Min length
    @NotNull
    private List<WriteOffResourceCountRequest> resources;

    public AddWriteOffRequest() {
    }

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
