package com.example.inventory.control.services;

import com.example.inventory.control.domain.models.Resource;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с ресурсами.
 */
public interface ResourceService {

    /**
     * Получить список всех ресурсов.
     *
     */
    List<Resource> getListAllResources();

    /**
     * Сохранить ресурс.
     *
     * @param resource сохраняемый ресурс.
     * @return сохраненный ресурс.
     */
    Resource save(Resource resource);

    /**
     * Получить ресурс по идентификатору.
     *
     * @param id идентификатор ресурса.
     */
    Optional<Resource> findById(Long id);

    /**
     * Проверить наличие ресурса по идентификатору.
     *
     * @param id идентификатор ресурса.
     * @return true - если ресурс есть иначе false.
     */
    boolean existsById(Long id);

    /**
     * Проверить наличие всех ресурсов по списку идентификаторов.
     *
     * @param ids идентификаторы ресурсов.
     * @return true - если есть все ресурсы с переданными идентификаторами иначе false.
     */
    boolean existsAllByIds(List<Long> ids);

    /**
     * Удалить ресурс по идентификатору.
     *
     * @param id идентификатор ресурса.
     */
    void deleteById(Long id);

}
