package com.example.inventory.control.services;

import com.example.inventory.control.domain.models.Move;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с перемещениями ресурсов.
 */
public interface MoveService {

    /**
     * Получить все перемещения.
     *
     * @return список всех перемещений.
     */
    List<Move> getAllMove();

    /**
     * Сохранить перемещение.
     *
     * @param move сохраняемое перемещение.
     *
     * @return сохраненное перемещение.
     */
    Move save(Move move);

    /**
     * Получить перемещение по идентификатору.
     *
     * @param id идентифкатор искомого перемещения.
     *
     * @return найденное перемещение.
     */
    Optional<Move> findById(Long id);
}
