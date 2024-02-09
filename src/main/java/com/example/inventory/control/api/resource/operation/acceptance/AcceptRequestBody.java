package com.example.inventory.control.api.resource.operation.acceptance;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Тело запроса для добавления приемки.
 */
public class AcceptRequestBody {

    /**
     * Идентификатор благодетеля.
     */
    @NotNull(message = "Благодетель отсутствует")
    private Long benefactorId;

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
    private List<AcceptResourceCountRequestBody> resources;

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

    public List<AcceptResourceCountRequestBody> getResources() {
        return resources;
    }

    public void setResources(List<AcceptResourceCountRequestBody> resources) {
        this.resources = resources;
    }
}
