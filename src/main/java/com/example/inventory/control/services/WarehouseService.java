package com.example.inventory.control.services;

import com.example.inventory.control.domain.models.Warehouse;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с местом хранения.
 */
public interface WarehouseService {

    /**
     * Получить список всех мест хранения.
     */
    List<Warehouse> getAllListWarehouses();

    /**
     * Получить место хранения по идентификатору.
     *
     * @param id идентификатор место хранения.
     */
    Optional<Warehouse> getWarehouseById(Long id);

    /**
     * Сохранить место хранения.
     *
     * @param warehouse сохраняемое место хранения.
     * @return сохраненное место хранения.
     */
    Warehouse save(Warehouse warehouse);

}
