package com.example.inventory.control.facades;

import com.example.inventory.control.api.remain.RemainingResponseBody;
import com.example.inventory.control.api.warehouse.RemainsResponseBody;
import com.example.inventory.control.api.warehouse.model.RemainResponseBodyModel;

/**
 * Фасад для работы с остатками ресурсов.
 */
public interface RemainingFacade {

    /**
     * Получить все остатки ресурсов.
     */
    RemainingResponseBody getAllRemaining();

    /**
     * Получить все остатки по идентификатору склада.
     *
     * @param warehouseId Идентификатор склада.
     *
     * @return ответ со статусом и списком остатков.
     */
    RemainsResponseBody getAllRemainsByWarehouseId(Long warehouseId);
}
