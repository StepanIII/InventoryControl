package com.example.inventory.control.api.resource.operation.inventory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Тело запроса "Ресурсы инвентаризации".
 */
public class InventoryResourceRequestBody {

    /**
     * Идентификатор ресурса.
     */
    @NotNull
    private Long resourceId;

    /**
     * Фактический остаток.
     */
    @NotNull
    @Min(value = 0, message = "Фактический остаток не может быть отрицательным")
    private Integer actualCount;

    /**
     * Расчетный остаток.
     */
    @NotNull
    private Integer settlementCount;

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getActualCount() {
        return actualCount;
    }

    public void setActualCount(Integer actualCount) {
        this.actualCount = actualCount;
    }

    public Integer getSettlementCount() {
        return settlementCount;
    }

    public void setSettlementCount(Integer settlementCount) {
        this.settlementCount = settlementCount;
    }
}
