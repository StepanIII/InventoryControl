package com.example.inventory.control.ui.models.responses.writeoff;

import com.example.inventory.control.ui.models.responses.BaseResponse;
import com.example.inventory.control.ui.models.responses.StatusResponse;
import com.example.inventory.control.ui.models.responses.warehouse.WarehouseResponse;

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
    private WarehouseResponse warehouse;

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
                                     LocalDateTime createdTime, WarehouseResponse warehouse,
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

    public WarehouseResponse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseResponse warehouse) {
        this.warehouse = warehouse;
    }

    public List<WriteOffResourceCountResponse> getResources() {
        return resources;
    }

    public void setResources(List<WriteOffResourceCountResponse> resources) {
        this.resources = resources;
    }
}
