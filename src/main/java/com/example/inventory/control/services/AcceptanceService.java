package com.example.inventory.control.services;

import com.example.inventory.control.domain.models.Accept;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с приемками.
 */
public interface AcceptanceService {

    /**
     * Получить список всех приемок.
     */
    List<Accept> getListAllAcceptance();

    /**
     * Сохранить приемку.
     *
     * @param accept сохраняемая приемка.
     * @return сохраненная приемка.
     */
    Accept save(Accept accept);

    /**
     * Получить приемку по идентификтору.
     *
     * @param id идентификатор приемки.
     * @return найденная приемка.
     */
    Optional<Accept> findById(Long id);
}
