package com.example.inventory.control.services;

import com.example.inventory.control.models.Resource;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с ресурсами.
 */
public interface ResourceService {

    /**
     * Сохранить ресурс.
     *
     * @param resource сохраняемый ресурс.
     * @return
     */
    Resource save(Resource resource);

    /**
     * Получить ресурс по идентификатору.
     *
     * @param id идентификатор ресурса.
     */
    Optional<Resource> findById(Long id);

    /**
     * Получить список всех ресурсов.
     *
     */
    List<Resource> getListAllResources();

    /**
     * Удалить ресурс по идентификатору.
     *
     * @param id идентификатор ресурса.
     */
    void deleteById(Long id);

    /**
     * Проверить наличие ресурса в базе данных по идентификатору.
     *
     * @param id идентификатор ресурса.
     * @return true - если ресурс есть в базе данных иначе false.
     */
    boolean existsById(Long id);

    /**
     * Получить список идентификаторов ресурсов по списку идентификаторов.
     *
     * @param ids список идентификаторов для поиска.
     */
    List<Long> findAllIds(List<Long> ids);
}
