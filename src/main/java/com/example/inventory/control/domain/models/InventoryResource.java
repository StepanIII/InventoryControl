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
     * Идентификатор ресурса.
     */
    private final Long resourceId;

    /**
     * Имя ресурса.
     */
    private final String name;

    /**
     * Размер.
     */
    private final String size;

    /**
     * Единица измерения.
     */
    private final String unit;

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

    public InventoryResource(Long id, Long resourceId, String name, String size, String unit, Integer actualCount, Integer estimatedCount, Integer difference) {
        CheckParamUtil.isNotNull("resourceId", resourceId);
        CheckParamUtil.isNotNull("actualCount", actualCount);
        CheckParamUtil.isNotNull("estimatedCount", estimatedCount);
        CheckParamUtil.isNotNull("difference", difference);

        this.id = id;
        this.resourceId = resourceId;
        this.name = name;
        this.size = size;
        this.unit = unit;
        this.actualCount = actualCount;
        this.estimatedCount = estimatedCount;
        this.difference = difference;
    }

    public static InventoryResource create(Long resourceId, Integer actualCount, Integer estimatedCount) {
        return new InventoryResource(null, resourceId, null, null, null, actualCount, estimatedCount, actualCount - estimatedCount);
    }

    public Optional<Long> id() {
        return Optional.ofNullable(id);
    }

    public Long getResourceId() {
        return resourceId;
    }

    public Optional<String> name() {
        return Optional.ofNullable(name);
    }

    public Optional<String> size() {
        return Optional.ofNullable(size);
    }

    public Optional<String> unit() {
        return Optional.ofNullable(unit);
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
