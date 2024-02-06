package com.example.inventory.control.facades;

import com.example.inventory.control.api.requests.writeOff.UpdateWriteOffRequest;
import com.example.inventory.control.api.writeoff.AddWriteOffRequest;
import com.example.inventory.control.api.writeoff.AddWriteOffResponse;
import com.example.inventory.control.api.writeoff.UpdateWriteOffResponse;
import com.example.inventory.control.api.writeoff.WriteOffResourcesResponse;
import com.example.inventory.control.api.writeoff.WriteOffsResponse;

/**
 * Фасад для работы со списаниями.
 */
public interface WriteOffFacade {

    /**
     * Получить список всех списаний.
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
     * Добавить новое списание.
     *
     * @param request запрос с данными нового списания.
     * @return ответ со статусом обработки.
     */
    AddWriteOffResponse addWriteOff(AddWriteOffRequest request);

    /**
     * Обновить списание.
     *
     * @param id       идентификатор списания.
     * @param request  запрос с обновляемым спсианием.
     * @return ответ со статусом обработки.
     */
    UpdateWriteOffResponse updateWriteOff(Long id, UpdateWriteOffRequest request);
}
