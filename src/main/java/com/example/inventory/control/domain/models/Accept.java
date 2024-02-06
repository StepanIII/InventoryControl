package com.example.inventory.control.domain.models;

import com.example.inventory.control.utils.CheckParamUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Доменная модель для сущности "Приемка".
 */
public final class Accept {

    /**
     * Идентификатор.
     */
    private final Long id;

    /**
     * Время создания.
     */
    private final LocalDateTime createdTime;

    /**
     * Место хранения.
     */
    private final Warehouse warehouse;

    /**
     * Благодетель.
     */
    private final Benefactor benefactor;

    /**
     * Добавленные ресурсы.
     */
    private final List<AcceptResourceCount> resources;

    public Accept(Long id, LocalDateTime createdTime, Warehouse warehouse, Benefactor benefactor, List<AcceptResourceCount> resources) {
        CheckParamUtil.isNotNull("warehouse", warehouse);
        CheckParamUtil.isNotNull("benefactor", benefactor);
        CheckParamUtil.isNotNull("resources", resources);

        this.id = id;
        this.createdTime = createdTime;
        this.warehouse = warehouse;
        this.benefactor = benefactor;
        this.resources = resources;
    }

    public static Accept create(Warehouse warehouse, Benefactor benefactor, List<AcceptResourceCount> resources) {
        return new Accept(null, null, warehouse, benefactor, resources);
    }

    public Accept updateWarehouse(Warehouse warehouse) {
        return new Accept(id, createdTime, warehouse, benefactor, resources);
    }

    public Accept updateBenefactor(Benefactor benefactor) {
        return new Accept(id, createdTime, warehouse, benefactor, resources);
    }

    public Accept updateResources(List<AcceptResourceCount> resources) {
        return new Accept(id, createdTime, warehouse, benefactor, resources);
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
