package com.example.inventory.control.api.warehouse;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.warehouse.model.WarehouseBody;

import java.util.List;

/**
 * Ответ на запрос получения всех мест хранения.
 */
public class WarehousesResponseBody extends BaseResponseBody {

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
