package com.example.inventory.control.models.responses;

import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import com.example.inventory.control.utils.CheckParamUtil;

import java.util.Objects;
import java.util.Optional;

/**
 *
 */
public final class ResourceResponse {

    /**
     * Идентификатор.
     */
    private Long id;

    /**
     * Наименование.
     */
    private String name;

    /**
     * Тип.
     */
    private ResourceType resourceType;


    /**
     * Еденица измерения.
     */
    private Units units;

    public ResourceResponse() {
    }

    public ResourceResponse(Long id, String name, ResourceType resourceType, Units units) {
        this.id = id;
        this.name = name;
        this.resourceType = resourceType;
        this.units = units;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public Units getUnits() {
        return units;
    }

    public void setUnits(Units units) {
        this.units = units;
    }

}
