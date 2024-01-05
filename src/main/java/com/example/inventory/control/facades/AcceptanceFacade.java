package com.example.inventory.control.facades;

import com.example.inventory.control.ui.models.responses.acceptance.AcceptanceResponse;

/**
 * Фасад для работы с приемками.
 */
public interface AcceptanceFacade {

    /**
     * Получить список всех приемок.
     */
    AcceptanceResponse getListAllAcceptance();
}
