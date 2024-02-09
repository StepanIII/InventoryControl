package com.example.inventory.control.facades;

import com.example.inventory.control.api.warehouse.RemainsResponseBody;
import com.example.inventory.control.api.warehouse.WarehousesResponseBody;

/**
 * Фасад для работы с местами хранения.
 */
public interface WarehouseFacade {

    /**
     * Получить все места хранения.
     */
    WarehousesResponseBody getAllWarehouses();

    /**
     * Получить остатки ресурсов на складе по идентификатору.
     *
     * @param id идентификатор склада.
     *
     * @return ответ со статусом и остатками ресурсов.
     */
    RemainsResponseBody getRemainsByWarehouseId(Long id);
}
