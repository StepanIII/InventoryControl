package com.example.inventory.control.domain.models;

import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Unit;
import com.example.inventory.control.utils.CheckParamUtil;

import java.util.Optional;

/**
 * Доменная модель сущности "Ресурсы".
 */
public final class Resource {

    /**
     * Идентификатор.
     */
    private final Long id;

    /**
     * Наименование.
     */
    private final String name;

    /**
     * Тип.
     */
    private final ResourceType type;

    /**
     * Еденица измерения.
     */
    private final Unit unit;

    private Resource(Long id, String name, ResourceType resourceType, Unit unit) {
        CheckParamUtil.isNotBlank("name", name);
        CheckParamUtil.isNotNull("resourceType", resourceType);
        CheckParamUtil.isNotNull("unit", unit);

        this.id = id;
        this.name = name;
        this.type = resourceType;
        this.unit = unit;
    }

    public static class Builder {

        private Long id;

        private String name;

        private ResourceType resourceType;

        private Unit unit;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setResourceType(ResourceType resourceType) {
            this.resourceType = resourceType;
            return this;
        }

        public Builder setUnits(Unit unit) {
            this.unit = unit;
            return this;
        }

        public Resource build() {
            return new Resource(id, name, resourceType, unit);
        }
    }

    public static Resource create(String name, ResourceType resourceType, Unit unit) {
        return new Resource(null, name, resourceType, unit);
    }

    public Resource updateName(String name) {
        return new Resource(id, name, type, unit);
    }

    public Resource updateType(ResourceType type) {
        return new Resource(id, name, type, unit);
    }

    public Resource updateUnits(Unit unit) {
        return new Resource(id, name, type, unit);
    }

    public Optional<Long> id() {
        return Optional.ofNullable(id);
    }

    public String getName() {
        return name;
    }

    public ResourceType getType() {
        return type;
    }

    public Unit getUnit() {
        return unit;
    }

}
