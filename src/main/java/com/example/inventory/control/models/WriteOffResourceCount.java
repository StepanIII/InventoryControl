package com.example.inventory.control.models;

import java.util.Objects;
import java.util.Optional;

public final class WriteOffResourceCount {

    private final Long id;

    private final Long resourceId;

    private final String name;

    private final Integer count;

    public WriteOffResourceCount(Long id, Long resourceId, String name, Integer count) {
        this.id = id;
        this.resourceId = resourceId;
        this.name = name;
        this.count = count;
    }

    public static WriteOffResourceCount create(Long resourceId, Integer count) {
        return new WriteOffResourceCount(null, resourceId, null, count);
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

    public WriteOffResourceCount updateCount(int count) {
        return new WriteOffResourceCount(id, resourceId, name, count);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WriteOffResourceCount that = (WriteOffResourceCount) o;
        return Objects.equals(resourceId, that.resourceId) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceId, name);
    }
}
