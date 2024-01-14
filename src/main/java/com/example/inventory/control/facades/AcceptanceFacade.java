package com.example.inventory.control.facades;

import com.example.inventory.control.ui.models.requests.acceptance.AddAcceptRequest;
import com.example.inventory.control.ui.models.requests.acceptance.UpdateAcceptRequest;
import com.example.inventory.control.ui.models.responses.acceptance.AcceptResourcesResponse;
import com.example.inventory.control.ui.models.responses.acceptance.AcceptanceResponse;
import com.example.inventory.control.ui.models.responses.acceptance.AddAcceptResponse;
import com.example.inventory.control.ui.models.responses.acceptance.UpdateAcceptResponse;

/**
 * Фасад для работы с приемками.
 */
public interface AcceptanceFacade {

    /**
     * Получить список всех приемок.
     */
    AcceptanceResponse getListAllAcceptance();

    /**
     * Добавить новую приемку.
     *
     * @param request запрос с данными новой приемки.
     * @return ответ со статусом и добавленной приемкой.
     */
    AddAcceptResponse addAccept(AddAcceptRequest request);

    /**
     * Получить приемку по идентификатору.
     *
     * @param id идентификатор приемки.
     * @return ответ со статусом и найденной приемкой.
     */
    AcceptResourcesResponse getAcceptById(Long id);

    /**
     * Обновить приемку.
     *
     * @param id      идентификатор приемки.
     * @param request запрос с данными для обновления.
     * @return ответ со статусом.
     */
    UpdateAcceptResponse updateAccept(Long id, UpdateAcceptRequest request);
}
