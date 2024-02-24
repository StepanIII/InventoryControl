package com.example.inventory.control.services;

import com.example.inventory.control.domain.models.Move;

import java.util.List;

/**
 * Сервис для работы с перемещениями ресурсов.
 */
public interface MoveService {

    List<Move> getAllMove();
}
