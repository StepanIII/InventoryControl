package com.example.inventory.control.ui.models.responses.acceptance;

import com.example.inventory.control.ui.models.responses.BaseResponse;
import com.example.inventory.control.ui.models.responses.StatusResponse;
import com.example.inventory.control.ui.models.responses.benefactor.BenefactorResponse;
import com.example.inventory.control.ui.models.responses.warehouse.WarehouseResponse;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 */
public class AcceptResourcesResponse extends BaseResponse {

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
    private WarehouseResponse warehouse;

    /**
     * Благодетель.
     */
    private BenefactorResponse benefactor;

    /**
     * Добавленные ресурсы.
     */
    private List<ResourceCountResponse> resources;

    public AcceptResourcesResponse() {
    }

    public AcceptResourcesResponse(StatusResponse statusResponse, String description) {
        super(statusResponse, description);
    }

    public AcceptResourcesResponse(StatusResponse statusResponse, String description, Long id,
                                   LocalDateTime createdTime, WarehouseResponse warehouse,
                                   BenefactorResponse benefactor, List<ResourceCountResponse> resources) {
        super(statusResponse, description);
        this.id = id;
        this.createdTime = createdTime;
        this.warehouse = warehouse;
        this.benefactor = benefactor;
        this.resources = resources;
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

    public WarehouseResponse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseResponse warehouse) {
        this.warehouse = warehouse;
    }

    public BenefactorResponse getBenefactor() {
        return benefactor;
    }

    public void setBenefactor(BenefactorResponse benefactor) {
        this.benefactor = benefactor;
    }

    public List<ResourceCountResponse> getResources() {
        return resources;
    }

    public void setResources(List<ResourceCountResponse> resources) {
        this.resources = resources;
    }
}
