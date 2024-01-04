package com.example.inventory.control.models.requests;

import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Модель запроса для изменения ресурса.
 */
public class UpdateResourceRequest {

    /**
     * Наименование.
     */
    @NotBlank
    private String name;

    /**
     * Тип.
     */
    @NotNull
    private ResourceType type;

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

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public Units getUnits() {
        return units;
    }

    public void setUnits(Units units) {
        this.units = units;
    }
}
