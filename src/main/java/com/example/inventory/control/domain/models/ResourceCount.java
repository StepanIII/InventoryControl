package com.example.inventory.control.domain.models;

import com.example.inventory.control.enums.Unit;

import java.util.Optional;

/**
 * Доменная модель количества ресурсов.
 */
public final class ResourceCount {

    private final Long id;

    private final Long resourceId;

    private final String size;

    private final String name;

    private final Unit unit;

    private final Integer count;

    public ResourceCount(Long id, Long resourceId, String size, String name, Unit unit, Integer count) {
        this.id = id;
        this.resourceId = resourceId;
        this.size = size;
        this.name = name;
        this.unit = unit;
        this.count = count;
    }

    public static ResourceCount create(Long resourceId, Integer count) {
        return new ResourceCount(null, resourceId, null, null, null, count);
    }

    public Optional<Long> id() {
        return Optional.ofNullable(id);
    }

    public Long getResourceId() {
        return resourceId;
    }

    public Optional<String> size() {
        return Optional.ofNullable(size);
    }

    public String getName() {
        return name;
    }

    public Unit getUnit() {
        return unit;
    }

    public Integer getCount() {
        return count;
    }
}
