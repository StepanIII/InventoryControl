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
 * Таблица количества ресурсов.
 */
@Entity
@Table(name = "RESOURCE_COUNT")
public class ResourceCountEntity {
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
     * Количество.
     */
    @Column(name = "COUNT")
    private Integer count;

    /**
     * Операция.
     */
    @ManyToOne
    @JoinColumn(name = "OPERATION_ID")
    private ResourceOperationEntity operation;

    public ResourceCountEntity() {
    }

    public ResourceCountEntity(Long id, ResourceEntity resource, Integer count, ResourceOperationEntity operation) {
        this.id = id;
        this.resource = resource;
        this.count = count;
        this.operation = operation;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ResourceOperationEntity getOperation() {
        return operation;
    }

    public void setOperation(ResourceOperationEntity operation) {
        this.operation = operation;
    }
}
