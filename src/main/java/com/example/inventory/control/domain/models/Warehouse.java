package com.example.inventory.control.domain.models;

import java.util.HashSet;
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

    public static Warehouse create(String name) {
        return new Warehouse(null, name, Set.of());
    }

    public Warehouse updateName(String name) {
        return new Warehouse(id, name, remains);
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

    public Warehouse addRemaining(List<ResourceCount> resourceCounts) {
        Set<Remain> newRemaining = new HashSet<>();
        for (ResourceCount resourceCount : resourceCounts) {
            Optional<Remain> remainCandidate = getRemainByResourceId(resourceCount.getResourceId());
            if (remainCandidate.isPresent()) {
                Remain updatedRemain = remainCandidate.get();
                updatedRemain = updatedRemain.updateCount(updatedRemain.getCount() + resourceCount.getCount());
                newRemaining.add(updatedRemain);
            } else {
                Remain newRemain = Remain.create(resourceCount.getResourceId(), resourceCount.getCount(), name);
                newRemaining.add(newRemain);
            }
        }
        newRemaining.addAll(remains);
        return new Warehouse(id, name, newRemaining);
    }

    // Пересмотреть доменную модель MoveResource и сдеалть списание одним методом
    public Warehouse addRemainingMove(List<MoveResource> resourceCounts) {
        Set<Remain> newRemaining = new HashSet<>();
        for (MoveResource resourceCount : resourceCounts) {
            Optional<Remain> remainCandidate = getRemainByResourceId(resourceCount.getResourceId());
            if (remainCandidate.isPresent()) {
                Remain updatedRemain = remainCandidate.get();
                updatedRemain = updatedRemain.updateCount(updatedRemain.getCount() + resourceCount.getCount());
                newRemaining.add(updatedRemain);
            } else {
                Remain newRemain = Remain.create(resourceCount.getResourceId(), resourceCount.getCount(), name);
                newRemaining.add(newRemain);
            }
        }
        newRemaining.addAll(remains);
        return new Warehouse(id, name, newRemaining);
    }

    public boolean hasAllResources(List<Long> checkedResourceIds) {
        for (Long resourceId : checkedResourceIds) {
            if (getRemainByResourceId(resourceId).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public Optional<Remain> getRemainByResourceId(long resourceId) {
        return remains.stream()
                .filter(r -> r.getResourceId().equals(resourceId))
                .findAny();
    }

    public boolean hasCountResources(long resourceId, int countResources) {
        Remain remain = remains.stream()
                .filter(r -> r.getResourceId().equals(resourceId))
                .findAny().orElseThrow();
        return remain.getCount() >= countResources;
    }

    public Warehouse writeOffRemaining(List<ResourceCount> resourceCounts) {
        Set<Remain> newRemaining = new HashSet<>();
        for (ResourceCount resourceCount : resourceCounts) {
            Optional<Remain> remainCandidate = getRemainByResourceId(resourceCount.getResourceId());
            if (remainCandidate.isPresent()) {
                Remain updatedRemain = remainCandidate.get();
                updatedRemain = updatedRemain.updateCount(updatedRemain.getCount() - resourceCount.getCount());
                newRemaining.add(updatedRemain);
            }
        }
        newRemaining.addAll(remains);
        return new Warehouse(id, name, newRemaining);
    }

    // Пересмотреть доменную модель MoveResource и сдеалть списание одним методом
    public Warehouse moveRemaining(List<MoveResource> resourceCounts) {
        Set<Remain> newRemaining = new HashSet<>();
        for (MoveResource resourceCount : resourceCounts) {
            Optional<Remain> remainCandidate = getRemainByResourceId(resourceCount.getResourceId());
            if (remainCandidate.isPresent()) {
                Remain updatedRemain = remainCandidate.get();
                updatedRemain = updatedRemain.updateCount(updatedRemain.getCount() - resourceCount.getCount());
                newRemaining.add(updatedRemain);
            }
        }
        newRemaining.addAll(remains);
        return new Warehouse(id, name, newRemaining);
    }

}
