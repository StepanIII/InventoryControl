package com.example.inventory.control.domain.models;

import com.example.inventory.control.utils.CheckParamUtil;

import java.util.Optional;

/**
 * Доменная модель ресурсы инвенаризации.
 */
public final class InventoryResource {

    /**
     * Идентификатор.
     */
    private final Long id;

    /**
     * Ресурс.
     */
    private final Resource resource;

    /**
     * Фактическое количесвто.
     */
    private final Integer actualCount;

    /**
     * Расчетное количество.
     */
    private final Integer estimatedCount;

    /**
     * Разница.
     */
    private final Integer difference;

    public InventoryResource(Long id, Resource resource, Integer actualCount, Integer estimatedCount, Integer difference) {
        CheckParamUtil.isNotNull("resource", resource);
        CheckParamUtil.isNotNull("actualCount", actualCount);
        CheckParamUtil.isNotNull("estimatedCount", estimatedCount);
        CheckParamUtil.isNotNull("difference", difference);

        this.id = id;
        this.resource = resource;
        this.actualCount = actualCount;
        this.estimatedCount = estimatedCount;
        this.difference = difference;
    }

    public static InventoryResource create(Resource resource, Integer actualCount, Integer estimatedCount) {
        return new InventoryResource(null, resource, actualCount, estimatedCount, actualCount - estimatedCount);
    }

    public Optional<Long> id() {
        return Optional.ofNullable(id);
    }

    public Resource getResource() {
        return resource;
    }

    public Integer getActualCount() {
        return actualCount;
    }

    public Integer getEstimatedCount() {
        return estimatedCount;
    }

    public Integer getDifference() {
        return difference;
    }
}
