package com.example.inventory.control.services;

import com.example.inventory.control.models.Acceptance;

import java.util.List;

/**
 * Сервис для работы с приемками.
 */
public interface AcceptanceService {

    /**
     * Получить список всех приемок.
     */
    List<Acceptance> getListAllAcceptance();

    /**
     * Сохранить новыую приемку.
     *
     * @param newAcceptance новая приемка.
     * @return сохраненная приемка.
     */
    Acceptance save(Acceptance newAcceptance);
}
