package com.example.inventory.control.facades;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.resource.operation.inventory.AllInventoryResponseBody;
import com.example.inventory.control.api.resource.operation.inventory.InventoryRequestBody;
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

    /**
     * Добавить инвентаризацию.
     *
     * @param request запрос с данными инвентаризации.
     *
     * @return ответ со статусом.
     */
    BaseResponseBody addInventory(InventoryRequestBody request);

    /**
     * Изменить инвентаризацию.
     *
     * @param id      идуентификатор обновляемой инвентаризации.
     * @param request запрос с обновленными данными инвентаризации.
     *
     * @return ответ со статусом.
     */
    BaseResponseBody editInventory(Long id, InventoryRequestBody request);

    /**
     * Удалить инвентаризацию
     *
     * @param id идуентификатор удаляемой инвентаризации.
     *
     * @return ответ со статусом.
     */
    BaseResponseBody deleteById(Long id);
}
