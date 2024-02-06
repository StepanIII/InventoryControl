package com.example.inventory.control.facades;

import com.example.inventory.control.api.acceptance.AcceptRequest;
import com.example.inventory.control.api.acceptance.AcceptResourcesResponse;
import com.example.inventory.control.api.acceptance.AcceptanceResponse;
import com.example.inventory.control.api.BaseResponse;

/**
 * Фасад для работы с приемками.
 */
public interface AcceptFacade {

    /**
     * Получить все приемки.
     */
    AcceptanceResponse getAllAcceptance();

    /**
     * Получить приемку по идентификатору.
     *
     * @param id идентификатор приемки.
     * @return ответ со статусом и найденной приемкой.
     */
    AcceptResourcesResponse getAcceptById(Long id);

    /**
     * Добавить приемку.
     *
     * @param request запрос с данными приемки.
     * @return ответ со статусом.
     */
    BaseResponse addAccept(AcceptRequest request);

//    /**
//     * Обновить приемку.
//     *
//     * @param id      идентификатор приемки.
//     * @param request запрос с данными для обновления.
//     * @return ответ со статусом.
//     */
//    BaseResponse updateAccept(Long id, AcceptRequest request);
}
