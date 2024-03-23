package com.example.inventory.control.services;

import com.example.inventory.control.domain.models.Role;

import java.util.Optional;

/**
 * Сервис для работы с ролями.
 */
public interface RoleService {

    /**
     * Получить роль по имени.
     *
     * @param name имя.
     *
     * @return найденная роль.
     */
    Optional<Role> findByName(String name);

}
