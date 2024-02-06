package com.example.inventory.control.facades;

import com.example.inventory.control.api.BaseResponse;
import com.example.inventory.control.api.writeoff.WriteOffRequest;
import com.example.inventory.control.api.writeoff.WriteOffResourcesResponse;
import com.example.inventory.control.api.writeoff.WriteOffsResponse;

/**
 * Фасад для работы со списаниями.
 */
public interface WriteOffFacade {

    /**
     * Получить все списания.
     */
    WriteOffsResponse getListAllWriteOff();

    /**
     * Получить списание по идентификатору.
     *
     * @param id идентификатор списания.
     * @return ответ со статусом и найденным списанием.
     */
    WriteOffResourcesResponse getWriteOffById(Long id);

    /**
     * Добавить списание.
     *
     * @param request запрос с данными нового списания.
     * @return ответ со статусом обработки.
     */
    BaseResponse addWriteOff(WriteOffRequest request);

//    /**
//     * Обновить списание.
//     *
//     * @param id       идентификатор списания.
//     * @param request  запрос с обновляемым спсианием.
//     * @return ответ со статусом обработки.
//     */
//    UpdateWriteOffResponse updateWriteOff(Long id, UpdateWriteOffRequest request);
}
