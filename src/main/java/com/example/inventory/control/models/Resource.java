package com.example.inventory.control.models;

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
    private final ResourceType resourceType;


    /**
     * Еденица измерения.
     */
    private final Units units;

    private Resource(Long id, String name, ResourceType resourceType, Units units) {
        CheckParamUtil.isNotBlank("name", name);
        CheckParamUtil.isNotNull("resourceType", resourceType);
        CheckParamUtil.isNotNull("units", units);

        this.id = id;
        this.name = name;
        this.resourceType = resourceType;
        this.units = units;
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
        return new Resource(id, name, resourceType, units);
    }

    public Resource updateType(ResourceType type) {
        return new Resource(id, name, type, units);
    }

    public Resource updateUnits(Units units) {
        return new Resource(id, name, resourceType, units);
    }

    public Optional<Long> id() {
        return Optional.ofNullable(id);
    }

    public String getName() {
        return name;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public Units getUnits() {
        return units;
    }

}
