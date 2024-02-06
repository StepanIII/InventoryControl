package com.example.inventory.control.facades;

import com.example.inventory.control.api.remaining.RemainingResponse;

/**
 * Фасад для работы с остатками.
 */
public interface RemainingFacade {

    /**
     * Получить все остатки.
     */
    RemainingResponse getListAllRemaining();

}
