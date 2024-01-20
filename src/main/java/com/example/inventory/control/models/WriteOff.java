package com.example.inventory.control.models;


import java.time.LocalDateTime;
import java.util.Optional;

public class WriteOff {

    /**
     * Идентификатор.
     */
    private Long id;

    /**
     * Время создания.
     */
    private LocalDateTime createdTime;

    /**
     * Место хранения.
     */
    private Warehouse warehouse;

    public WriteOff(Long id, LocalDateTime createdTime, Warehouse warehouse) {
        this.id = id;
        this.createdTime = createdTime;
        this.warehouse = warehouse;
    }

    public Optional<Long> id() {
        return Optional.ofNullable(id);
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

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
