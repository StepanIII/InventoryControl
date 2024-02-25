package com.example.inventory.control.api.resource.operation.move.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Модель тела ответа "Перемещение с ресурсами".
 */
public class MoveWithResourcesResponseBodyModel {

    /**
     * Идентификатор.
     */
    private Long id;

    /**
     * Время создания.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    /**
     * Имя склада откуда производится перемещение.
     */
    private String fromWarehouseName;

    /**
     * Имя склада куда производится перемещение.
     */
    private String toWarehouseName;

    /**
     * Ресурсы.
     */
    private List<MoveResourceResponseBodyModel> resources;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getFromWarehouseName() {
        return fromWarehouseName;
    }

    public void setFromWarehouseName(String fromWarehouseName) {
        this.fromWarehouseName = fromWarehouseName;
    }

    public String getToWarehouseName() {
        return toWarehouseName;
    }

    public void setToWarehouseName(String toWarehouseName) {
        this.toWarehouseName = toWarehouseName;
    }

    public List<MoveResourceResponseBodyModel> getResources() {
        return resources;
    }

    public void setResources(List<MoveResourceResponseBodyModel> resources) {
        this.resources = resources;
    }
}
