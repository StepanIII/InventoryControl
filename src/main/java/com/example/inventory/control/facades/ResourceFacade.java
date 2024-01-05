package com.example.inventory.control.facades;

import com.example.inventory.control.ui.models.requests.AddResourceRequest;
import com.example.inventory.control.ui.models.requests.UpdateResourceRequest;
import com.example.inventory.control.ui.models.responses.resource.AddResourceResponse;
import com.example.inventory.control.ui.models.responses.resource.DeleteResourceResponse;
import com.example.inventory.control.ui.models.responses.resource.ResourcesResponse;
import com.example.inventory.control.ui.models.responses.resource.UpdateResourceResponse;

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
