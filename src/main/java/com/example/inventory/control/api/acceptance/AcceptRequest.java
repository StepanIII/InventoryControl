package com.example.inventory.control.api.acceptance;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Запрос для добавления приемки.
 */
public class AcceptRequest {

    /**
     * Идентификатор благодетеля.
     */
    @NotNull
    private Long benefactorId;

    /**
     * Идентификатор места хранения.
     */
    @NotNull
    private Long warehouseId;

    /**
     * Добавляемые ресурсы.
     */
    @NotNull
    @Size(min = 1)
    private List<ResourceCountRequest> resources;

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
