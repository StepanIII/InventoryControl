package com.example.inventory.control.facades;

import com.example.inventory.control.api.warehouse.WarehousesResponse;

/**
 * Фасад для работы с местами хранения.
 */
public interface WarehouseFacade {

    /**
     * Получить все места хранения.
     */
    WarehousesResponse getListAllWarehouses();

}
