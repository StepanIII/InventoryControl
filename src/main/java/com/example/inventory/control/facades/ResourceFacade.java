package com.example.inventory.control.facades;

import com.example.inventory.control.api.resources.ResourceRequest;
import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.resources.ResourceResponseBody;
import com.example.inventory.control.api.resources.ResourceTypesResponseBody;
import com.example.inventory.control.api.resources.ResourceUnitsResponseBody;
import com.example.inventory.control.api.resources.ResourcesResponseBody;

/**
 * Фасад для работы с ресурсами.
 */
public interface ResourceFacade {

    /**
     * Получить все ресурсы.
     *
     * @return ответ со статусом и ресурсами.
     */
    ResourcesResponseBody getAllResources();

    /**
     * Получить ресурс по идентификатору.
     *
     * @param id идентификатор ресурса.
     * @return ответ со статусом и ресурсами.
     */
    ResourceResponseBody getResourceById(Long id);

    /**
     * Добавить ресурс.
     *
     * @param request запрос с данными нового ресурса.
     * @return ответ со статусом и добавленным ресурсом.
     */
    ResourceResponseBody addResource(ResourceRequest request);

    /**
     * Изменить ресурс.
     *
     * @param id      идентификатор обновляемого ресурса.
     * @param request запрос с обновленными данными ресурса.
     * @return ответ со статусом и обновленным ресурсом.
     */
    ResourceResponseBody updateResource(Long id, ResourceRequest request);

    /**
     * Удалить ресурс.
     *
     * @param id идентификатор удаляемого ресурса.
     * @return ответ со статусом.
     */
    BaseResponseBody deleteResource(Long id);

    /**
     * Получить типы ресурсов.
     *
     * @return ответ со статусом и типами ресурсов.
     */
    ResourceTypesResponseBody getAllResourceTypes();

    /**
     * Получить единицы измерения ресурсов.
     *
     * @return ответ со статусом и единицами измерения ресурсов.
     */
    ResourceUnitsResponseBody getAllResourceUnits();

}
