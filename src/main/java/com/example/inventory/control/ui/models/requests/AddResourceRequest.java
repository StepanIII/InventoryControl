package com.example.inventory.control.ui.models.requests;

import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Модель запроса для добавления нового ресурса.
 */
public class AddResourceRequest {

    /**
     * Наименование.
     */
    @NotBlank
    private String name;

    /**
     * Тип.
     */
    @NotNull
    private ResourceType resourceType;

    /**
     * Еденицы измерения.
     */
    @NotNull
    private Units units;

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
