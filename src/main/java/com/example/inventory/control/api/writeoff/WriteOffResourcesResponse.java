package com.example.inventory.control.api.writeoff;

import com.example.inventory.control.api.warehouse.model.WarehouseBody;
import com.example.inventory.control.api.responses.BaseResponse;
import com.example.inventory.control.api.responses.StatusResponse;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 */
public class WriteOffResourcesResponse extends BaseResponse {

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
     * Списанные ресурсы.
     */
    private List<WriteOffResourceCountResponse> resources;

    public WriteOffResourcesResponse() {
    }

    public WriteOffResourcesResponse(StatusResponse statusResponse, String description) {
        super(statusResponse, description);
    }

    public WriteOffResourcesResponse(StatusResponse statusResponse, String description, Long id,
                                     LocalDateTime createdTime, WarehouseBody warehouse,
                                     List<WriteOffResourceCountResponse> resources) {
        super(statusResponse, description);
        this.id = id;
        this.createdTime = createdTime;
        this.warehouse = warehouse;
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

    public WarehouseBody getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseBody warehouse) {
        this.warehouse = warehouse;
    }

    public List<WriteOffResourceCountResponse> getResources() {
        return resources;
    }

    public void setResources(List<WriteOffResourceCountResponse> resources) {
        this.resources = resources;
    }
}
