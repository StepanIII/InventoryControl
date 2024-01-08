package com.example.inventory.control.facades;

import com.example.inventory.control.ui.models.responses.warehouse.WarehousesResponse;

/**
 * Фасад для работы с местом хранения
 */
public interface WarehouseFacade {

    /**
     * Получить список всех мест хранения.
     */
    WarehousesResponse getListAllWarehouses();

}
