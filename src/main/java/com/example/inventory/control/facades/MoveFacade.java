package com.example.inventory.control.facades;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptResponseBody;
import com.example.inventory.control.api.resource.operation.move.AllMoveResponseBody;
import com.example.inventory.control.api.resource.operation.move.MoveRequestBody;
import com.example.inventory.control.api.resource.operation.move.MoveResponseBody;

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

    /**
     * Добавить перемещение.
     *
     * @param request запрос с данными перемещения.
     *
     * @return ответ со статусом.
     */
    BaseResponseBody addMove(MoveRequestBody request);

    /**
     * Получить перемещение по идентификатору.
     *
     * @param id идентификатор получаемого перемещения.
     *
     * @return ответ со стутусом и перемещением.
     */
    MoveResponseBody getMoveById(Long id);
}
