package com.example.inventory.control.facades;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.resources.ResourceResponseBody;
import com.example.inventory.control.api.warehouse.RemainsResponseBody;
import com.example.inventory.control.api.warehouse.WarehouseRequest;
import com.example.inventory.control.api.warehouse.WarehouseResponseBody;
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

    /**
     * Добавить склад.
     *
     * @param request запрос с данными склада.
     *
     * @return ответ со статусом.
     */
    BaseResponseBody addWarehouse(WarehouseRequest request);

    /**
     * Удалить склад.
     *
     * @param id идентификатор склада.
     *
     * @return ответ со статусом.
     */
    BaseResponseBody deleteWarehouse(Long id);

    /**
     * Получить склад по идентификатору.
     *
     * @param id идентификатор склада.
     *
     * @return ответ со статусом и складом.
     */
    WarehouseResponseBody getWarehouseById(Long id);

    /**
     * Обновить склад.
     *
     * @param id      идентификатор.
     * @param request запрос с данными склада.
     *
     * @return ответ со статусом.
     */
    BaseResponseBody updateWarehouse(Long id, WarehouseRequest request);
}
