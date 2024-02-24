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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MOVES")
public class MoveEntity {

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
     * Склад откуда производится перемещение.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FROM_WAREHOUSE_ID", nullable = false)
    private WarehouseEntity fromWarehouse;

    /**
     * Склад куда производится перемещение.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TO_WAREHOUSE_ID", nullable = false)
    private WarehouseEntity toWarehouse;

    /**
     * Ресурсы.
     */
    @OneToMany(mappedBy = "move", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MoveResourceEntity> resources = new ArrayList<>();

    public MoveEntity() {
    }

    public MoveEntity(Long id, WarehouseEntity fromWarehouse, WarehouseEntity toWarehouse) {
        this.id = id;
        this.fromWarehouse = fromWarehouse;
        this.toWarehouse = toWarehouse;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    @PrePersist
    @PreUpdate
    public void setCreatedTime() {
        this.createdTime = LocalDateTime.now();
    }

    public WarehouseEntity getFromWarehouse() {
        return fromWarehouse;
    }

    public void setFromWarehouse(WarehouseEntity fromWarehouse) {
        this.fromWarehouse = fromWarehouse;
    }

    public WarehouseEntity getToWarehouse() {
        return toWarehouse;
    }

    public void setToWarehouse(WarehouseEntity toWarehouse) {
        this.toWarehouse = toWarehouse;
    }

    public List<MoveResourceEntity> getResources() {
        return resources;
    }

}
