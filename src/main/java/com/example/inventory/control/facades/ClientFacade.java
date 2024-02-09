package com.example.inventory.control.facades;

import com.example.inventory.control.api.client.benefactor.BenefactorsResponseBody;
import com.example.inventory.control.api.client.benefactor.BeneficiariesResponseBody;

/**
 * Фасад для работы с клиентами.
 */
public interface ClientFacade {

    /**
     * Получить всех благодетелей.
     */
    BenefactorsResponseBody getAllBenefactors();

    /**
     * Получить всех благополучателей.
     *
     * @return тело ответа со статусом и благополучателями.
     */
    BeneficiariesResponseBody getAllBeneficiaries();
}
