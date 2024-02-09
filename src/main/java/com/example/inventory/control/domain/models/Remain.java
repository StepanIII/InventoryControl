package com.example.inventory.control.domain.models;

import com.example.inventory.control.enums.Unit;

import java.util.Objects;
import java.util.Optional;

// Привязать к складу
public class Remain {

    private final Long id;

    private final Long resourceId;

    private final String resourceName;

    private final Integer count;

    private final Unit unit;

    private final String warehouseName;

    public Remain(Long id, Long resourceId, String resourceName, Integer count, Unit unit, String warehouseName) {
        this.id = id;
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.count = count;
        this.unit = unit;
        this.warehouseName = warehouseName;
    }

    public static Remain create(Long resourceId, Integer count, String warehouseName) {
        return new Remain(null, resourceId, null, count, null, warehouseName);
    }

    public Optional<Long> id() {
        return Optional.ofNullable(id);
    }

    public Long getResourceId() {
        return resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public Integer getCount() {
        return count;
    }

    public Unit getUnit() {
        return unit;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public Remain updateCount(int count) {
        return new Remain(id, resourceId, resourceName, count, unit, warehouseName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Remain remain = (Remain) o;
        return Objects.equals(resourceId, remain.resourceId) && Objects.equals(resourceName, remain.resourceName) && Objects.equals(warehouseName, remain.warehouseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceId, resourceName, warehouseName);
    }
}
