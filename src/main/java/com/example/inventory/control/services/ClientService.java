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
     * Получить список всех клиентов.
     *
     * @return список найденных клиентов.
     */
    List<Client> getAllClient();

    /**
     * Получить клиента по идентификатору и типу.
     *
     * @param id   идентификатор клиента.
     * @param type тип клиента.
     *
     * @return найденный клиент.
     */
    Optional<Client> getClientByIdAndType(Long id, ClientType type);

    /**
     * Получить клиента по идентификатору.
     *
     * @param id идентификатор.
     *
     * @return найденный клиент.
     */
    Optional<Client> getClientById(Long id);

    /**
     * Сохранить клиента.
     *
     * @param client сохраняемый клиент.
     *
     * @return сохраненный клиент.
     */
    Client save(Client client);

    /**
     * Проверить наличие клиента по идентфиикатору.
     *
     * @param id идентификатор клиента.
     *
     * @return true если клиент есть иначе false.
     */
    boolean existsById(Long id);

    /**
     * Удалить клиента по идентифкатору.
     *
     * @param id идентификатор клиента.
     */
    void deleteById(Long id);
}
