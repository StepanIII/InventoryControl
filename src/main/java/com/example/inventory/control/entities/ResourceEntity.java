package com.example.inventory.control.entities;

import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Objects;

// Порефакторить

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
    @Column(name = "PRODUCT_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ResourceType resourceType;

//    /**
//     * Описание.
//     */
//    @Column(name = "DESCRIPTION")
//    private String description;

    /**
     * Еденица измерения.
     */
    @Column(name = "UNITS", nullable = false)
    @Enumerated(EnumType.STRING)
    private Units units;

//    /**
//     * Количество.
//     */
//    @Column(name = "COUNT", nullable = false)
//    private Integer count;

//    /**
//     * Склад хранения.
//     */
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "WAREHOUSE_ID", nullable = false)
//    private WarehouseEntity warehouse;
//
//    /**
//     * Поставщик.
//     */
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "SUPPLIER_ID", nullable = false)
//    private SupplierEntity supplier;
//
//    /**
//     * Время добавления.
//     */
//    private LocalDateTime addedTime;

    public ResourceEntity() {
    }

    public ResourceEntity(Long id, String name, ResourceType resourceType, Units units) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceEntity that = (ResourceEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && resourceType == that.resourceType && units == that.units;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, resourceType, units);
    }

    @Override
    public String toString() {
        return "ResourceEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", resourceType=" + resourceType +
                ", units=" + units +
                '}';
    }
}
