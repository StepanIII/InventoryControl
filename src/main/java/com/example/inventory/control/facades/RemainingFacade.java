package com.example.inventory.control.facades;

import com.example.inventory.control.api.remain.RemainingResponseBody;

/**
 * Фасад для работы с остатками ресурсов.
 */
public interface RemainingFacade {

    /**
     * Получить все остатки ресурсов.
     */
    RemainingResponseBody getAllRemaining();

}
