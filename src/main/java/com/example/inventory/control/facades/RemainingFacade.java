package com.example.inventory.control.facades;

import com.example.inventory.control.api.responses.remaining.RemainingResponse;

/**
 * Фасад для работы с остатками.
 */
public interface RemainingFacade {

    /**
     * Получить список всех остатков.
     */
    RemainingResponse getListAllRemaining();


}
