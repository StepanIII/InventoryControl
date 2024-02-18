package com.example.inventory.control.api.resource.operation.inventory;


import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.resource.operation.acceptance.model.AcceptResponseBodyModel;
import com.example.inventory.control.api.resource.operation.inventory.model.InventoryResponseBodyModel;

import java.util.List;

/**
 * Тело ответа "Инвентаризации".
 */
public class AllInventoryResponseBody extends BaseResponseBody {

    /**
     * Инвентаризации.
     */
    private List<InventoryResponseBodyModel> inventory;

    public List<InventoryResponseBodyModel> getInventory() {
        return inventory;
    }

    public void setInventory(List<InventoryResponseBodyModel> inventory) {
        this.inventory = inventory;
    }
}
