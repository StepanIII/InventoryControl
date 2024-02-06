package com.example.inventory.control.services;

import com.example.inventory.control.domain.models.Benefactor;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с благодетелями
 */
public interface BenefactorService {

    /**
     * Получить список всех благодетелей.
     */
    List<Benefactor> getListAllBenefactors();

    /**
     * Получить благодетеля по идентификатору.
     * @param id идентификатор благодетеля.
     */
    Optional<Benefactor> getBenefactorById(Long id);
}
