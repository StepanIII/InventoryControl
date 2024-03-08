package com.example.inventory.control.api.resources.model;

import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Unit;

// Переделать DTO на Response или создать две модели DTO и Response
/**
 * Тело ответа "Ресурс".
 */
public class ResourceDto {

    /**
     * Идентификатор.
     */
    private Long id;

    /**
     * Наименование.
     */
    private String name;

    /**
     * Размер.
     */
    private String size;

    /**
     * Тип.
     */
    private ResourceType type;

    /**
     * Еденица измерения.
     */
    private Unit unit;

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
