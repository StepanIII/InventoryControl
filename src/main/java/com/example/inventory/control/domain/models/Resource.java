package com.example.inventory.control.domain.models;

import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
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
    private final Units unit;

    private Resource(Long id, String name, ResourceType resourceType, Units unit) {
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

        private Units units;

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

        public Builder setUnits(Units units) {
            this.units = units;
            return this;
        }

        public Resource build() {
            return new Resource(id, name, resourceType, units);
        }
    }

    public static Resource create(String name, ResourceType resourceType, Units units) {
        return new Resource(null, name, resourceType, units);
    }

    public Resource updateName(String name) {
        return new Resource(id, name, type, unit);
    }

    public Resource updateType(ResourceType type) {
        return new Resource(id, name, type, unit);
    }

    public Resource updateUnits(Units units) {
        return new Resource(id, name, type, units);
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

    public Units getUnit() {
        return unit;
    }

}
