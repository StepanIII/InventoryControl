package com.example.inventory.control.models;

import java.time.LocalDateTime;
import java.util.Optional;

public final class Acceptance {

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

    /**
     * Благодетель.
     */
    private Benefactor benefactor;

    public Acceptance(Long id, LocalDateTime createdTime, Warehouse warehouse, Benefactor benefactor) {
        // Проверка на обязательность
        this.id = id;
        this.createdTime = createdTime;
        this.warehouse = warehouse;
        this.benefactor = benefactor;
    }

    public static Acceptance create(Warehouse warehouse, Benefactor benefactor) {
        return new Acceptance(null, null, warehouse, benefactor);
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

    public Benefactor getBenefactor() {
        return benefactor;
    }

    public void setBenefactor(Benefactor benefactor) {
        this.benefactor = benefactor;
    }
}
