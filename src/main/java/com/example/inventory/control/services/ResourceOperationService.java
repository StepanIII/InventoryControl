package com.example.inventory.control.services;

import com.example.inventory.control.domain.models.ResourceOperation;
import com.example.inventory.control.enums.ResourceOperationType;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с операциями над ресурсами.
 */
public interface ResourceOperationService {

    /**
     * Получить список операций над ресурсами по типу.
     *
     * @param type тип операции.
     *
     * @return найденные типы операаций.
     */
    List<ResourceOperation> getAllResourceOperationByType(ResourceOperationType type);

    /**
     * Получить операцию над ресурсами по идентификатору и типу.
     *
     * @param id идентификатор операции.
     *
     * @return найденная операция.
     */
    Optional<ResourceOperation> findResourceOperationByIdAndType(Long id, ResourceOperationType type);

    /**
     * Сохранить операцию над ресурсами.
     *
     * @param resourceOperation сохраняемая операция.
     *
     * @return сохраненная операция.
     */
    ResourceOperation saveOperation(ResourceOperation resourceOperation);

}
