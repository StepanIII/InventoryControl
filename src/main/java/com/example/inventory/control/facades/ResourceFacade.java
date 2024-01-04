package com.example.inventory.control.facades;

import com.example.inventory.control.models.requests.AddResourceRequest;
import com.example.inventory.control.models.requests.UpdateResourceRequest;
import com.example.inventory.control.models.responses.AddResourceResponse;
import com.example.inventory.control.models.responses.DeleteResourceResponse;
import com.example.inventory.control.models.responses.ResourcesResponse;
import com.example.inventory.control.models.responses.UpdateResourceResponse;

/**
 * Фасад для работы с ресурсами.
 */
public interface ResourceFacade {

    /**
     * Добавить новый ресурс.
     *
     * @param request запрос с данными нового ресурса.
     * @return ответ со статусом и добавленным ресурсом.
     */
    AddResourceResponse addResource(AddResourceRequest request);

    /**
     * Изменить ресурс.
     *
     * @param id      идентификатор обновляемого ресурса.
     * @param request запрос с измененными данными ресурса.
     * @return ответ со статусом.
     */
    UpdateResourceResponse updateResource(Long id, UpdateResourceRequest request);

    /**
     * Получить список всех ресурсов.
     */
    ResourcesResponse getListAllResources();

    /**
     * Удалить ресурс.
     *
     * @param id идентификатор удаляемого ресурса.
     * @return ответ со статусом.
     */
    DeleteResourceResponse deleteResource(Long id);
}
