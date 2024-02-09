package com.example.inventory.control.services;

import com.example.inventory.control.domain.models.Client;
import com.example.inventory.control.enums.ClientType;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с клиентами.
 */
public interface ClientService {

    /**
     * Получить список всех клиентов по типу.
     *
     * @param type тип клиента.
     *
     * @return список найденных клиентов.
     */
    List<Client> getAllClient(ClientType type);

    /**
     * Получить клиента по идентификатору и типу.
     *
     * @param id   идентификатор клиента.
     * @param type тип клиента.
     *
     * @return найденный клиент.
     */
    Optional<Client> getClientByIdAndType(Long id, ClientType type);
}
