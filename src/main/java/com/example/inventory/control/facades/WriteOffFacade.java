package com.example.inventory.control.facades;

import com.example.inventory.control.ui.models.responses.acceptance.AcceptanceResponse;
import com.example.inventory.control.ui.models.responses.writeoff.WriteOffsResponse;

/**
 * Фасад для работы со списаниями.
 */
public interface WriteOffFacade {

    /**
     * Получить список всех приемок.
     */
    WriteOffsResponse getListAllWriteOff();

}
