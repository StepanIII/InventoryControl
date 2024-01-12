package com.example.inventory.control.ui.models.requests.acceptance;

import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Модель запроса для добавления новой приемки.
 */
public class AddAcceptRequest {

    @NotNull
    private Long benefactorId;

    @NotNull
    private Long warehouseId;

    @NotNull
    private List<ResourceCountRequest> resources;

    public AddAcceptRequest() {
    }

    public AddAcceptRequest(Long benefactorId, Long warehouseId, List<ResourceCountRequest> resources) {
        this.benefactorId = benefactorId;
        this.warehouseId = warehouseId;
        this.resources = resources;
    }

    public Long getBenefactorId() {
        return benefactorId;
    }

    public void setBenefactorId(Long benefactorId) {
        this.benefactorId = benefactorId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public List<ResourceCountRequest> getResources() {
        return resources;
    }

    public void setResources(List<ResourceCountRequest> resources) {
        this.resources = resources;
    }
}
