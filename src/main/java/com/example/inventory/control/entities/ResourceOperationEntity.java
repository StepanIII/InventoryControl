package com.example.inventory.control.entities;

import com.example.inventory.control.enums.ResourceOperationType;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Таблица операций с ресурсами.
 */
@Entity
@Table(name = "RESOURCE_OPERATIONS")
public class ResourceOperationEntity {

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
     * Тип.
     */
    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ResourceOperationType type;

    /**
     * Место хранения.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WAREHOUSE_ID", nullable = false)
    private WarehouseEntity warehouse;

    /**
     * Клиент.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID")
    private ClientEntity client;

    /**
     * Ресурсы.
     */
    @OneToMany(mappedBy = "operation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResourceCountEntity> resourceCounts = new ArrayList<>();

    public ResourceOperationEntity() {
    }

    public ResourceOperationEntity(Long id, ResourceOperationType type, WarehouseEntity warehouse, ClientEntity client) {
        this.id = id;
        this.type = type;
        this.warehouse = warehouse;
        this.client = client;
    }

    @PrePersist
    public void setCreatedTime() {
        createdTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }


    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public ResourceOperationType getType() {
        return type;
    }

    public void setType(ResourceOperationType type) {
        this.type = type;
    }

    public WarehouseEntity getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseEntity warehouse) {
        this.warehouse = warehouse;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity benefactor) {
        this.client = benefactor;
    }

    public List<ResourceCountEntity> getResourceCounts() {
        return resourceCounts;
    }

}
