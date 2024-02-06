package com.example.inventory.control.domain.models;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public final class Warehouse {

    /**
     * Идентификатор.
     */
    private final Long id;

    /**
     * Наименование.
     */
    private final String name;

    private final Set<Remain> remains;

    public Warehouse(Long id, String name, Set<Remain> remains) {
        this.id = id;
        this.name = name;
        this.remains = remains;
    }

    public Optional<Long> id() {
        return Optional.ofNullable(id);
    }

    public String getName() {
        return name;
    }

    public Set<Remain> getRemains() {
        return remains;
    }

    public Warehouse addResources(List<AcceptResourceCount> newResources) {
        for (AcceptResourceCount newResource : newResources) {
            Optional<Remain> updatedRemainCandidate = remains.stream()
                    .filter(r -> r.getResourceId().equals(newResource.getResourceId()))
                    .findAny();
            if (updatedRemainCandidate.isPresent()) {
                Remain updatedRemain = updatedRemainCandidate.get();
                updatedRemain.updateCount(updatedRemain.getCount() + newResource.getCount());
            } else {
                Remain newRemain = Remain.create(newResource.getResourceId(), newResource.getCount(), name);
                remains.add(newRemain);
            }
        }
        return this;
    }

    public boolean hasAllResources(List<Long> checkedResourceIds) {
        return remains.stream()
                .map(Remain::getResourceId)
                .toList()
                .containsAll(checkedResourceIds);
    }

    public boolean hasCountResources(long resourceId, int countResources) {
        Remain remain = remains.stream()
                .filter(r -> r.getResourceId().equals(resourceId))
                .findAny().orElseThrow();
        return remain.getCount() >= countResources;
    }

    public Warehouse writeOffResources(long resourceId, int countResources) {
        Remain remain = remains.stream()
                .filter(r -> r.getResourceId().equals(resourceId))
                .findAny().orElseThrow();
        remains.remove(remain);
        remain = remain.updateCount(remain.getCount() - countResources);
        remains.add(remain);
        return this;
    }

}
