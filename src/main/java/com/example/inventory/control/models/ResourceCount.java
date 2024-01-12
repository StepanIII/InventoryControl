package com.example.inventory.control.models;

import java.util.Optional;

public final class ResourceCount {

    private final Long id;

    private final Long resourceId;

    private final Integer count;

    public ResourceCount(Long id, Long resourceId, Integer count) {
        this.id = id;
        this.resourceId = resourceId;
        this.count = count;
    }

    public static ResourceCount create(Long resourceId, Integer count) {
        return new ResourceCount(null, resourceId, count);
    }

    public Optional<Long> id() {
        return Optional.ofNullable(id);
    }

    public Long getResourceId() {
        return resourceId;
    }

    public Integer getCount() {
        return count;
    }
}
