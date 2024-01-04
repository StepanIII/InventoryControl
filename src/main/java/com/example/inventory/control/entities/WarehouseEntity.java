package com.example.inventory.control.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

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
     * Список товаров.
     */
//    @OneToMany(mappedBy = "warehouse")
//    private List<ResourceEntity> resourceEntities = new ArrayList<>();


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

}
