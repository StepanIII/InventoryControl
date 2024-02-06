package com.example.inventory.control.facades;

import com.example.inventory.control.api.resources.ResourceRequest;
import com.example.inventory.control.api.responses.BaseResponse;
import com.example.inventory.control.api.resources.ResourceResponse;
import com.example.inventory.control.api.resources.ResourcesResponse;

/**
 * Фасад для работы с ресурсами.
 */
public interface ResourceFacade {

    /**
     * Получить все ресурсы.
     *
     * @return ответ со статусом и ресурсами.
     */
    ResourcesResponse getAllResources();

    /**
     * Добавить ресурс.
     *
     * @param request запрос с данными нового ресурса.
     * @return ответ со статусом и добавленным ресурсом.
     */
    ResourceResponse addResource(ResourceRequest request);

    /**
     * Изменить ресурс.
     *
     * @param id      идентификатор обновляемого ресурса.
     * @param request запрос с обновленными данными ресурса.
     * @return ответ со статусом и обновленным ресурсом.
     */
    ResourceResponse updateResource(Long id, ResourceRequest request);

    /**
     * Удалить ресурс.
     *
     * @param id идентификатор удаляемого ресурса.
     * @return ответ со статусом.
     */
    BaseResponse deleteResource(Long id);
}
