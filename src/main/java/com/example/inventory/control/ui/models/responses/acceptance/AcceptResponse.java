package com.example.inventory.control.ui.models.responses.acceptance;

import com.example.inventory.control.entities.BenefactorEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Units;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

/**
 *
 */
public class AcceptResponse {

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
    private String warehouse;

    /**
     * Благодетель.
     */
    private String benefactor;

    public AcceptResponse() {
    }

    public AcceptResponse(Long id, LocalDateTime createdTime, String warehouse, String benefactor) {
        this.id = id;
        this.createdTime = createdTime;
        this.warehouse = warehouse;
        this.benefactor = benefactor;
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

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getBenefactor() {
        return benefactor;
    }

    public void setBenefactor(String benefactor) {
        this.benefactor = benefactor;
    }
}
