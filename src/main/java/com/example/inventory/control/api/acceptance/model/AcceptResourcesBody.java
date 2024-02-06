package com.example.inventory.control.api.acceptance.model;

import com.example.inventory.control.api.benefactor.model.BenefactorBody;
import com.example.inventory.control.api.warehouse.model.WarehouseBody;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Тело ответа "Приемка с ресурсами".
 */
public class AcceptResourcesBody {

    /**
     * Идентификатор.
     */
    private Long id;

    /**
     * Время создания.
     */
    private LocalDateTime createdTime;

    /**
     * Место хранения.
     */
    private WarehouseBody warehouse;

    /**
     * Благодетель.
     */
    private BenefactorBody benefactor;

    /**
     * Добавленные ресурсы.
     */
    private List<ResourceCountBody> resources;

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

    public WarehouseBody getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseBody warehouse) {
        this.warehouse = warehouse;
    }

    public BenefactorBody getBenefactor() {
        return benefactor;
    }

    public void setBenefactor(BenefactorBody benefactor) {
        this.benefactor = benefactor;
    }

    public List<ResourceCountBody> getResources() {
        return resources;
    }

    public void setResources(List<ResourceCountBody> resources) {
        this.resources = resources;
    }
}
