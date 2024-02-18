package com.example.inventory.control.api.resource.operation.inventory;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.resource.operation.acceptance.model.AcceptWithResourcesBodyModel;
import com.example.inventory.control.api.resource.operation.inventory.model.InventoryWithResourcesBodyModel;

/**
 * Ответ на запрос получения инвентаризации.
 */
public class InventoryResponseBody extends BaseResponseBody {

    /**
     * Инвентаризация.
     */
    private InventoryWithResourcesBodyModel inventory;

    public InventoryWithResourcesBodyModel getInventory() {
        return inventory;
    }

    public void setInventory(InventoryWithResourcesBodyModel inventory) {
        this.inventory = inventory;
    }
}
