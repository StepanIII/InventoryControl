package com.example.inventory.control.ui.models.requests.writeOff;

import com.example.inventory.control.ui.models.responses.writeoff.WriteOffResourceCountRequest;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Модель запроса для обновления приемки.
 */
public class UpdateWriteOffRequest {

    @NotNull
    private Long warehouseId;

    @NotNull
    private List<WriteOffResourceCountRequest> resources;

    public UpdateWriteOffRequest() {
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public List<WriteOffResourceCountRequest> getResources() {
        return resources;
    }

    public void setResources(List<WriteOffResourceCountRequest> resources) {
        this.resources = resources;
    }
}
