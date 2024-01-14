package com.example.inventory.control.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Сущность таблицы "WAREHOUSES" (Склады).
 */
@Entity
@Table(name = "WAREHOUSES")
public class WarehouseEntity {

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
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    /**
     * Хранимые ресурсы.
     */
    @OneToMany(mappedBy = "warehouse",fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
    private Set<WarehouseResourceCountEntity> resourceCounts = new HashSet<>(); // переделать на set для сохранения уникальности


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

    public Set<WarehouseResourceCountEntity> getResourceCounts() {
        return resourceCounts;
    }
}
