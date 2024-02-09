package com.example.inventory.control.domain.models;

import java.util.Optional;

/**
 * Доменная модель количества ресурсов.
 */
public final class ResourceCount {

    private final Long id;

    private final Long resourceId;

    private final String name;

    private final Integer count;

    public ResourceCount(Long id, Long resourceId, String name, Integer count) {
        this.id = id;
        this.resourceId = resourceId;
        this.name = name;
        this.count = count;
    }

    public static ResourceCount create(Long resourceId, Integer count) {
        return new ResourceCount(null, resourceId, null, count);
    }

    public Optional<Long> id() {
        return Optional.ofNullable(id);
    }

    public Long getResourceId() {
        return resourceId;
    }

    public String getName() {
        return name;
    }

    public Integer getCount() {
        return count;
    }
}
