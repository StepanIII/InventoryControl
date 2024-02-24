package com.example.inventory.control.facades;

import com.example.inventory.control.api.resource.operation.move.AllMoveResponseBody;

/**
 * Фасад для работы с перемещениями ресурсов.
 */
public interface MoveFacade {

    /**
     * Получить вск перемещения.
     *
     * @return ответ со статусом и перемещениями.
     */
    AllMoveResponseBody getAllMove();
}
