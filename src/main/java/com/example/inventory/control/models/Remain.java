package com.example.inventory.control.models;

import java.util.Optional;

// Привязать к складу
public class Remain {

    private final Long id;

    private final Long resourceId;

    private final String resourceName;

    private final Integer count;

    private final String warehouseName;

    public Remain(Long id, Long resourceId, String resourceName, Integer count, String warehouseName) {
        this.id = id;
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.count = count;
        this.warehouseName = warehouseName;
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

    public String getWarehouseName() {
        return warehouseName;
    }
}
