package com.example.inventory.control.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public final class Acceptance {

    /**
     * Идентификатор.
     */
    private final Long id;

    /**
     * Время создания.
     */
    private final LocalDateTime createdTime; // Переделать на время обновления?

    /**
     * Место хранения.
     */
    private final Warehouse warehouse;

    /**
     * Благодетель.
     */
    private final Benefactor benefactor;

    private final List<AcceptResourceCount> resources;

    public Acceptance(Long id, LocalDateTime createdTime, Warehouse warehouse, Benefactor benefactor, List<AcceptResourceCount> resources) {
        // Проверка на обязательность
        this.id = id;
        this.createdTime = createdTime;
        this.warehouse = warehouse;
        this.benefactor = benefactor;
        this.resources = resources;
    }

    public static Acceptance create(Warehouse warehouse, Benefactor benefactor, List<AcceptResourceCount> resources) {
        return new Acceptance(null, null, warehouse, benefactor, resources);
    }

    public Acceptance updateWarehouse(Warehouse warehouse) {
        return new Acceptance(id, createdTime, warehouse, benefactor, resources);
    }

    public Acceptance updateBenefactor(Benefactor benefactor) {
        return new Acceptance(id, createdTime, warehouse, benefactor, resources);
    }

    public Acceptance updateResources(List<AcceptResourceCount> resources) {
        return new Acceptance(id, createdTime, warehouse, benefactor, resources);
    }

    public Optional<Long> id() {
        return Optional.ofNullable(id);
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Benefactor getBenefactor() {
        return benefactor;
    }

    public List<AcceptResourceCount> getResources() {
        return resources;
    }
}
