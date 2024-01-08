package com.example.inventory.control.facades;

import com.example.inventory.control.ui.models.responses.benefactor.BenefactorsResponse;

/**
 * Фасад для работы с благодетелями.
 */
public interface BenefactorFacade {

    /**
     * Получить список всех благодетелей.
     */
    BenefactorsResponse getListAllBenefactors();

}
