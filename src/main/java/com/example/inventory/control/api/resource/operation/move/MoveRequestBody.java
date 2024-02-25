package com.example.inventory.control.api.resource.operation.move;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Тело запроса для добавления перемещения.
 */
public class MoveRequestBody {

    /**
     * Идентификатор склада откуда производится перемещение.
     */
    @NotNull(message = "Склад откуда производится перемещение не указан")
    private Long fromWarehouseId;

    /**
     * Идентификатор склада куда производится перемещение.
     */
    @NotNull(message = "Склад куда производится перемещение не указан")
    private Long toWarehouseId;

    /**
     * Добавляемые ресурсы.
     */
    @NotNull(message = "Ресурсы не выбраны")
    @Size(min = 1, message = "Ресурсы не выбраны")
    private List<MoveResourceRequestBody> resources;

    public Long getFromWarehouseId() {
        return fromWarehouseId;
    }

    public void setFromWarehouseId(Long fromWarehouseId) {
        this.fromWarehouseId = fromWarehouseId;
    }

    public Long getToWarehouseId() {
        return toWarehouseId;
    }

    public void setToWarehouseId(Long toWarehouseId) {
        this.toWarehouseId = toWarehouseId;
    }

    public List<MoveResourceRequestBody> getResources() {
        return resources;
    }

    public void setResources(List<MoveResourceRequestBody> resources) {
        this.resources = resources;
    }
}
