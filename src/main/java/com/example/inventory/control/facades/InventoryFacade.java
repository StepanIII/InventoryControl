package com.example.inventory.control.facades;

import com.example.inventory.control.api.resource.operation.acceptance.AllAcceptResponseBody;
import com.example.inventory.control.api.resource.operation.inventory.AllInventoryResponseBody;
import com.example.inventory.control.api.resource.operation.inventory.InventoryResponseBody;

/**
 * Фасад для работы с инвенатризацией.
 */
public interface InventoryFacade {

    /**
     * Получить все инвентаризации.
     *
     * @return ответ со статусом и списком всех инвентаризаций.
     */
    AllInventoryResponseBody getAllInventory();

    /**
     * Получить инвентаризацию по идентификатору.
     *
     * @param id идентификтаор инвентаризации.
     *
     * @return ответ со статусом и найденной инвентаризацией.
     */
    InventoryResponseBody getInventoryById(Long id);
}
