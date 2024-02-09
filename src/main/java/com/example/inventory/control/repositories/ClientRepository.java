package com.example.inventory.control.repositories;

import com.example.inventory.control.entities.ClientEntity;
import com.example.inventory.control.enums.ClientType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с клиентами.
 */
@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    /**
     * Получить список всех клиентов по типу.
     *
     * @param type тип клиента.
     *
     * @return список всех найденных клиентов.
     */
    List<ClientEntity> findAllByType(ClientType type);

    /**
     * Получить клиента по идентификатору и типу.
     *
     * @param id   идентификтаор клиента.
     * @param type тип клиента.
     *
     * @return найденный клиент.
     */
    Optional<ClientEntity> findByIdAndType(Long id, ClientType type);
}
