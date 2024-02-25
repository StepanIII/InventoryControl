package com.example.inventory.control.domain.models;

import com.example.inventory.control.utils.CheckParamUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Доменная модель перемещений ресурсов.
 */
public final class Move {

    /**
     * Идентификатор.
     */
    private final Long id;

    /**
     * Время создания.
     */
    private final LocalDateTime createdTime;

    /**
     * Склад откуда производится перемещение.
     */
    private final Warehouse fromWarehouse;

    /**
     * Склад куда производится перемещение.
     */
    private final Warehouse toWarehouse;

    /**
     * Ресурсы.
     */
    private final List<MoveResource> resources;

    public Move(Long id, LocalDateTime createdTime, Warehouse fromWarehouse, Warehouse toWarehouse, List<MoveResource> resources) {
        CheckParamUtil.isNotNull("fromWarehouse", fromWarehouse);
        CheckParamUtil.isNotNull("toWarehouse", toWarehouse);
        CheckParamUtil.isNotEmpty("resources", resources);

        this.id = id;
        this.createdTime = createdTime;
        this.fromWarehouse = fromWarehouse;
        this.toWarehouse = toWarehouse;
        this.resources = resources;
    }

    public static Move create(Warehouse fromWarehouse, Warehouse toWarehouse, List<MoveResource> resources) {
        return new Move(null, null, fromWarehouse, toWarehouse, resources);
    }

    public Optional<Long> id() {
        return Optional.ofNullable(id);
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public Warehouse getFromWarehouse() {
        return fromWarehouse;
    }

    public Warehouse getToWarehouse() {
        return toWarehouse;
    }

    public List<MoveResource> getResources() {
        return resources;
    }
}
