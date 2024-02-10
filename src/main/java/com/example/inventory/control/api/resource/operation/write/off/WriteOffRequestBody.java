package com.example.inventory.control.api.resource.operation.write.off;

import com.example.inventory.control.api.ResourceCountRequestBody;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Тело запроса для добавления списания.
 */
public class WriteOffRequestBody {

    /**
     * Идентификатор места хранения.
     */
    @NotNull(message = "Склад отсутствует")
    private Long warehouseId;

    /**
     * Причина.
     */
    private String description;

    /**
     * Добавляемые ресурсы.
     */
    @NotNull(message = "Ресурсы не выбраны")
    @Size(min = 1, message = "Ресурсы не выбраны")
    private List<ResourceCountRequestBody> resources;

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ResourceCountRequestBody> getResources() {
        return resources;
    }

    public void setResources(List<ResourceCountRequestBody> resources) {
        this.resources = resources;
    }
}
