package com.example.inventory.control.api.resource.operation.issue;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Тело запроса для добавления выдачи.
 */
public class IssueRequestBody {

    /**
     * Идентификатор благополучателя.
     */
    @NotNull(message = "Благополучатель отсутствует")
    private Long beneficiaryId;

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
    private List<IssueResourceCountRequestBody> resources;

    public Long getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(Long beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public List<IssueResourceCountRequestBody> getResources() {
        return resources;
    }

    public void setResources(List<IssueResourceCountRequestBody> resources) {
        this.resources = resources;
    }
}
