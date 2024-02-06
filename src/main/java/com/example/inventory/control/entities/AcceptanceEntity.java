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
import java.util.List;

/**
 * Сущность таблицы "ACCEPTANCE" (Приемки).
 */
@Entity
@Table(name = "ACCEPTANCE")
public class AcceptanceEntity {

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
     * Благодетель.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BENEFACTOR_ID", nullable = false)
    private BenefactorEntity benefactor;

    /**
     * Добавленные ресурсы.
     */
    @OneToMany(mappedBy = "acceptance", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AcceptResourceCountEntity> resourceCounts = new ArrayList<>();

    public AcceptanceEntity() {
    }

    public AcceptanceEntity(Long id, WarehouseEntity warehouse, BenefactorEntity benefactor) {
        this.id = id;
        this.warehouse = warehouse;
        this.benefactor = benefactor;
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

    public WarehouseEntity getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseEntity warehouse) {
        this.warehouse = warehouse;
    }

    public BenefactorEntity getBenefactor() {
        return benefactor;
    }

    public void setBenefactor(BenefactorEntity benefactor) {
        this.benefactor = benefactor;
    }

    public List<AcceptResourceCountEntity> getResourceCounts() {
        return resourceCounts;
    }

}
