package com.example.inventory.control.services;

import com.example.inventory.control.domain.models.Inventory;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с инвентаризацией.
 */
public interface InventoryService {

    /**
     * Получить все инвентаризации.
     *
     * @return список всех инвентаризаций.
     */
    List<Inventory> getAllInventory();

    /**
     * Получить инвентаризацию по идентификатору.
     *
     * @param id идентификатор инвентаризации.
     *
     * @return найденная инвентаризация.
     */
    Optional<Inventory> findInventoryById(Long id);

    /**
     * Сохранить инвентаризацию.
     *
     * @param inventory сохраняемая инвентаризация.
     *
     * @return сохраненная инвентаризация.
     */
    Inventory save(Inventory inventory);

    /**
     * Проверка наличия инвентаризации по идентификатору.
     *
     * @param id идентификатор инвентризации.
     *
     * @return true если есть иначе false
     */
    boolean existsById(Long id);

    /**
     * Удалить инвентризацию.
     *
     * @param id идентификатор инвентризации.
     */
    void deleteById(Long id);
}
