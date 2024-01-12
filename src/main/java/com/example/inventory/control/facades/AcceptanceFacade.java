package com.example.inventory.control.facades;

import com.example.inventory.control.ui.models.requests.acceptance.AddAcceptRequest;
import com.example.inventory.control.ui.models.responses.acceptance.AcceptanceResponse;
import com.example.inventory.control.ui.models.responses.acceptance.AddAcceptResponse;

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
}
