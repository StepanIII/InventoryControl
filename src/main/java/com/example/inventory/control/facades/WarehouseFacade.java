package com.example.inventory.control.facades;

import com.example.inventory.control.api.warehouse.WarehousesResponse;

/**
 * Фасад для работы с местом хранения
 */
public interface WarehouseFacade {

    /**
     * Получить список всех мест хранения.
     */
    WarehousesResponse getListAllWarehouses();

}
