package com.example.inventory.control.ui.models.responses.writeoff;

import java.time.LocalDateTime;

public class WriteOffResponse {

    private Long id;

    private LocalDateTime createdTime;

    private String warehouseName;

    public WriteOffResponse() {
    }

    public WriteOffResponse(Long id, LocalDateTime createdTime, String warehouseName) {
        this.id = id;
        this.createdTime = createdTime;
        this.warehouseName = warehouseName;
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

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
}
