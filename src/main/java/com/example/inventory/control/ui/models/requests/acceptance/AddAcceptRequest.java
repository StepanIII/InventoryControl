package com.example.inventory.control.ui.models.requests.acceptance;

import jakarta.validation.constraints.NotNull;

/**
 * Модель запроса для добавления новой приемки.
 */
public class AddAcceptRequest {

    @NotNull
    private Long benefactorId;

    @NotNull
    private Long warehouseId;

    public AddAcceptRequest() {
    }

    public AddAcceptRequest(Long benefactorId, Long warehouseId) {
        this.benefactorId = benefactorId;
        this.warehouseId = warehouseId;
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
}
