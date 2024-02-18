package com.example.inventory.control.domain.models;

import com.example.inventory.control.utils.CheckParamUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Доменная модель инвентаризации.
 */
public final class Inventory {

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
     * Ресурсы.
     */
    private final List<InventoryResource> resources;

    public Inventory(Long id, LocalDateTime createdTime, Warehouse warehouse, List<InventoryResource> resources) {
        CheckParamUtil.isNotNull("warehouse", warehouse);
        CheckParamUtil.isNotNull("resources", resources);

        this.id = id;
        this.createdTime = createdTime;
        this.warehouse = warehouse;
        this.resources = resources;
    }

    public static Inventory create(Warehouse warehouse, List<InventoryResource> resources) {
        return new Inventory(null, null, warehouse, resources);
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

    public List<InventoryResource> getResources() {
        return resources;
    }
}
