package com.example.inventory.control.facades;

import com.example.inventory.control.api.benefactor.BenefactorsResponse;

/**
 * Фасад для работы с благодетелями.
 */
public interface BenefactorFacade {

    /**
     * Получить всех благодетелей.
     */
    BenefactorsResponse getAllBenefactors();

}
