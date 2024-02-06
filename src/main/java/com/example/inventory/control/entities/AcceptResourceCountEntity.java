package com.example.inventory.control.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

// переделать таблицу по аналогии с WarehouseResourceCountEntity
@Entity
@Table(name = "ACCEPT_RESOURCE_COUNT")
public class AcceptResourceCountEntity {
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
     * Приемка.
     */
    @ManyToOne
    @JoinColumn(name = "ACCEPT_ID")
    private AcceptanceEntity acceptance;

    public AcceptResourceCountEntity() {
    }

    public AcceptResourceCountEntity(Long id, ResourceEntity resource, Integer count, AcceptanceEntity acceptance) {
        this.id = id;
        this.resource = resource;
        this.count = count;
        this.acceptance = acceptance;
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

    public AcceptanceEntity getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(AcceptanceEntity acceptance) {
        this.acceptance = acceptance;
    }
}
