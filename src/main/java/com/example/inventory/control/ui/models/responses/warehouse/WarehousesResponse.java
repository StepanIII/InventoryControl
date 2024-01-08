package com.example.inventory.control.ui.models.responses.warehouse;

import java.util.List;

public class WarehousesResponse {

    private List<WarehouseResponse> warehouses;

    public WarehousesResponse() {
    }

    public WarehousesResponse(List<WarehouseResponse> warehouses) {
        this.warehouses = warehouses;
    }

    public List<WarehouseResponse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<WarehouseResponse> warehouses) {
        this.warehouses = warehouses;
    }
}
