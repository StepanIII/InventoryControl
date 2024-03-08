package com.example.inventory.control.domain.models;

import com.example.inventory.control.utils.CheckParamUtil;

import java.util.Optional;

/**
 * Доменная модель ресурсы перемещения.
 */
public final class MoveResource {

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
    private final Integer count;


    public MoveResource(Long id, Long resourceId, String name, String size, String unit, Integer count) {
        CheckParamUtil.isNotNull("resourceId", resourceId);
        CheckParamUtil.isNotNull("count", count);

        this.id = id;
        this.resourceId = resourceId;
        this.name = name;
        this.size = size;
        this.unit = unit;
        this.count = count;
    }

    public static MoveResource create(Long resourceId, Integer count) {
        return new MoveResource(null, resourceId, null, null, null, count);
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

    public Integer getCount() {
        return count;
    }
}
