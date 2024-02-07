package com.example.inventory.control.api.resources;

import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Запрос добавления ресурса.
 */
public class ResourceRequest {

    /**
     * Наименование.
     */
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    /**
     * Тип.
     */
    @NotNull
    private ResourceType type;

    /**
     * Еденица измерения.
     */
    @NotNull
    private Units unit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public Units getUnit() {
        return unit;
    }

    public void setUnit(Units unit) {
        this.unit = unit;
    }
}
