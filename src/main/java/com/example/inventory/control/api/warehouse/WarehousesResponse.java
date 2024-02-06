package com.example.inventory.control.api.warehouse;

import com.example.inventory.control.api.BaseResponse;
import com.example.inventory.control.api.warehouse.model.WarehouseBody;

import java.util.List;

/**
 * Ответ на запрос получения всех мест хранения.
 */
public class WarehousesResponse extends BaseResponse {

    /**
     * Места хранения.
     */
    private List<WarehouseBody> warehouses;

    public List<WarehouseBody> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<WarehouseBody> warehouses) {
        this.warehouses = warehouses;
    }
}
