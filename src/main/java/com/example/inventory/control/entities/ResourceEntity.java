package com.example.inventory.control.entities;

import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Unit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// Добавить размер
/**
 * Сущность таблицы "RESOURCES" (Ресурсы).
 */
@Entity
@Table(name = "RESOURCES")
public class ResourceEntity {

    /**
     * Идентификатор.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Наименование.
     */
    @Column(name = "NAME", nullable = false)
    private String name;

    /**
     * Тип.
     */
    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ResourceType type;

    /**
     * Еденица измерения.
     */
    @Column(name = "UNIT", nullable = false)
    @Enumerated(EnumType.STRING)
    private Unit unit;

    public ResourceEntity() {
    }

    public ResourceEntity(Long id, String name, ResourceType resourceType, Unit unit) {
        this.id = id;
        this.name = name;
        this.type = resourceType;
        this.unit = unit;
    }

    public Long getId() {
        return id;
    }

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

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
