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

@Entity
@Table(name = "SUPPLIER")
public class SupplierEntity {

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
    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    /**
     * Список товаров.
     */
//    @OneToMany(mappedBy = "supplier")
//    private List<ResourceEntity> resourceEntities = new ArrayList<>(); // List или set

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
