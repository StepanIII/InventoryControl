package com.example.inventory.control.services;

import com.example.inventory.control.domain.models.Role;

import java.util.List;
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

    /**
     * Получить все роли.
     *
     * @return список всех ролей.
     */
    List<Role> findAll();

    /**
     * Получить все роли по именам.
     *
     * @return список всех ролей.
     * @param names
     */
    List<Role> findAllByNames(List<String> names);

}
