package com.example.inventory.control.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Сущность для таблицы инвентаризации.
 */
@Entity
@Table(name = "INVENTORY")
public class InventoryEntity {

    /**
     * Идентификатор.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Время создания.
     */
    @Column(name = "CREATED_TIME", nullable = false)
    private LocalDateTime createdTime;

    /**
     * Склад.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WAREHOUSE_ID", nullable = false)
    private WarehouseEntity warehouse;

    /**
     * Ресурсы инвентаризации.
     */
    @OneToMany(mappedBy = "inventory", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InventoryResourceEntity> resources = new ArrayList<>();

    public InventoryEntity() {
    }

    public InventoryEntity(Long id, LocalDateTime createdTime, WarehouseEntity warehouse) {
        this.id = id;
        this.createdTime = createdTime;
        this.warehouse = warehouse;
    }

    public Long getId() {
        return id;
    }


    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    @PrePersist
    public void setCreatedTime() {
        this.createdTime = LocalDateTime.now();
    }

    public WarehouseEntity getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseEntity warehouse) {
        this.warehouse = warehouse;
    }

    public List<InventoryResourceEntity> getResources() {
        return resources;
    }

    public void setResources(List<InventoryResourceEntity> resources) {
        this.resources = resources;
    }
}
