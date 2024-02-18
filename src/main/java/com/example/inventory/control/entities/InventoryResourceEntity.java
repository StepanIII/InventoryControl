package com.example.inventory.control.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Сущность для таблицы ресурсов инвенаризации.
 */
@Entity
@Table(name = "INVENTORY_RESOURCES")
public class InventoryResourceEntity {
    /**
     * Идентификатор.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ресурс.
     */
    @ManyToOne
    @JoinColumn(name = "RESOURCE_ID")
    private ResourceEntity resource;

    /**
     * Ивентаризация.
     */
    @ManyToOne
    @JoinColumn(name = "INVENTORY_ID")
    private InventoryEntity inventory;

    /**
     * Фактическое количество.
     */
    @Column(name = "ACTUAL_COUNT")
    private Integer actualCount;

    /**
     * Расчетное количество.
     */
    @Column(name = "ESTIMATED_COUNT")
    private Integer estimatedCount;

    /**
     * Разница.
     */
    @Column(name = "DIFFERENCE")
    private Integer difference;

    public InventoryResourceEntity() {
    }

    public InventoryResourceEntity(Long id, ResourceEntity resource, InventoryEntity inventory, Integer actualCount, Integer estimatedCount, Integer difference) {
        this.id = id;
        this.resource = resource;
        this.inventory = inventory;
        this.actualCount = actualCount;
        this.estimatedCount = estimatedCount;
        this.difference = difference;
    }

    public Long getId() {
        return id;
    }

    public ResourceEntity getResource() {
        return resource;
    }

    public void setResource(ResourceEntity resource) {
        this.resource = resource;
    }

    public InventoryEntity getInventory() {
        return inventory;
    }

    public void setInventory(InventoryEntity inventory) {
        this.inventory = inventory;
    }

    public Integer getActualCount() {
        return actualCount;
    }

    public void setActualCount(Integer actualCount) {
        this.actualCount = actualCount;
    }

    public Integer getEstimatedCount() {
        return estimatedCount;
    }

    public void setEstimatedCount(Integer estimatedCount) {
        this.estimatedCount = estimatedCount;
    }

    public Integer getDifference() {
        return difference;
    }

    public void setDifference(Integer difference) {
        this.difference = difference;
    }
}
