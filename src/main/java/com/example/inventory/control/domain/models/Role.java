package com.example.inventory.control.domain.models;

import com.example.inventory.control.utils.CheckParamUtil;

import java.util.Optional;

/**
 * Доменная модель Роль"".
 */
public final class Role {

    /**
     * Идентификатор.
     */
    private final Long id;

    /**
     * Имя.
     */
    private final String name;

    public Role(Long id, String name) {
        CheckParamUtil.isNotBlank("name", name);

        this.id = id;
        this.name = name;
    }

    public static Role create(String name) {
        return new Role(null, name);
    }

    public Optional<Long> id() {
        return Optional.ofNullable(id);
    }

    public String getName() {
        return name;
    }
}
