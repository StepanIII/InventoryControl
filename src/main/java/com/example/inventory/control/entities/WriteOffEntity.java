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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Сущность таблицы "WRITE_OFF" (Списания).
 */
@Entity
@Table(name = "WRITE_OFF")
public class WriteOffEntity {

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
     * Место хранения.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WAREHOUSE_ID", nullable = false)
    private WarehouseEntity warehouse;

    /**
     * Списанные ресурсы.
     */
    @OneToMany(mappedBy = "writeOff",fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
    private Set<WriteOffResourceCountEntity> resourceCounts = new HashSet<>();

    @PrePersist
    public void setCreatedTime() {
        createdTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public WarehouseEntity getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseEntity warehouse) {
        this.warehouse = warehouse;
    }

    public Set<WriteOffResourceCountEntity> getResourceCounts() {
        return resourceCounts;
    }

    public void setResourceCounts(Set<WriteOffResourceCountEntity> resourceCounts) {
        this.resourceCounts = resourceCounts;
    }
}
