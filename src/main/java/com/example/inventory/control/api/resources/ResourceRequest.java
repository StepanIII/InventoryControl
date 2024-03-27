package com.example.inventory.control.api.resources;

import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Unit;
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
    @NotBlank(message = "Наименование не должно быть пустым")
    @Size(min = 1, max = 255, message = "Количество символов наименования должно быть в диапозоне от 1 до 255")
    private String name;

    /**
     * Размер.
     */
    private String size;

    /**
     * Тип.
     */
    @NotNull(message = "Укажите тип ресурса.")
    private ResourceType type;

    /**
     * Еденица измерения.
     */
    @NotNull(message = "Укажите единицу измерения ресурса")
    private Unit unit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
