package com.example.inventory.control.api.warehouse;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.resources.model.ResourceDto;
import com.example.inventory.control.api.warehouse.model.WarehouseBody;

/**
 * Ответ на запрос склада.
 */
public class WarehouseResponseBody extends BaseResponseBody {

    /**
     * Склад.
     */
    private WarehouseBody warehouse;

    public WarehouseBody getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseBody warehouse) {
        this.warehouse = warehouse;
    }
}
