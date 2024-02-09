package com.example.inventory.control.repositories;

import com.example.inventory.control.entities.ResourceOperationEntity;
import com.example.inventory.control.enums.ResourceOperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с операциями над ресурсами.
 */
@Repository
public interface ResourceOperationRepository extends JpaRepository<ResourceOperationEntity, Long> {

    /**
     * Получить все операции по типу.
     *
     * @param type тип операции.
     *
     * @return список полученых операции.
     */
    List<ResourceOperationEntity> findAllByType(ResourceOperationType type);

    /**
     * Получить операцию над ресурсами по идентификатору и типу.
     *
     * @param id   идентификатор операции.
     * @param type тип операции.
     *
     * @return найденная операция.
     */
    Optional<ResourceOperationEntity> findByIdAndType(Long id, ResourceOperationType type);

}
