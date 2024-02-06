package com.example.inventory.control.services;

import com.example.inventory.control.domain.models.Remain;

import java.util.List;

/**
 * Сервис для работы с остатками.
 */
public interface RemainingService {

    /**
     * Получить список всех остатков.
     */
    List<Remain> getListRemaining();

}
