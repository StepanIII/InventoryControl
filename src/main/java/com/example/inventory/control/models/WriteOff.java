package com.example.inventory.control.models;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WriteOff {

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

    private final List<WriteOffResourceCount> writeOffResourceCounts;

    public WriteOff(Long id, LocalDateTime createdTime, Warehouse warehouse, List<WriteOffResourceCount> writeOffResourceCounts) {
        this.id = id;
        this.createdTime = createdTime;
        this.warehouse = warehouse;
        this.writeOffResourceCounts = writeOffResourceCounts;
    }

    public static WriteOff create(Warehouse warehouse, List<WriteOffResourceCount> writeOffResourceCounts) {
        return new WriteOff(null, null, warehouse, writeOffResourceCounts);
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

    public List<WriteOffResourceCount> getWriteOffResourceCounts() {
        return writeOffResourceCounts;
    }

    public WriteOff updateResourceCount(long resourceId, int count) {
        List<WriteOffResourceCount> newResourceCount = new ArrayList<>(writeOffResourceCounts);
        WriteOffResourceCount resourceCount = writeOffResourceCounts.stream().filter(w -> w.getResourceId() == resourceId).findAny().orElseThrow();
        newResourceCount.remove(resourceCount);
        resourceCount = resourceCount.updateCount(count);
        newResourceCount.add(resourceCount);
        return new WriteOff(id, createdTime, warehouse, newResourceCount);
    }

    public WriteOffResourceCount getByResourceId(long resourceId) {
        return writeOffResourceCounts.stream().filter(w -> w.getResourceId() == resourceId).findAny().orElseThrow();
    }
}
