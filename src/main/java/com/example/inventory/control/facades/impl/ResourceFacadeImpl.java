package com.example.inventory.control.facades.impl;

import com.example.inventory.control.facades.ResourceFacade;
import com.example.inventory.control.models.Resource;
import com.example.inventory.control.ui.models.requests.AddResourceRequest;
import com.example.inventory.control.ui.models.requests.UpdateResourceRequest;
import com.example.inventory.control.ui.models.responses.resource.AddResourceResponse;
import com.example.inventory.control.ui.models.responses.resource.DeleteResourceResponse;
import com.example.inventory.control.ui.models.responses.resource.ResourcesResponse;
import com.example.inventory.control.ui.models.responses.StatusResponse;
import com.example.inventory.control.ui.models.responses.resource.UpdateResourceResponse;
import com.example.inventory.control.services.ResourceService;
import com.example.inventory.control.services.mapper.ResourceMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public final class ResourceFacadeImpl implements ResourceFacade {

    private final ResourceService resourceService;
    private final ResourceMapper resourceMapper;

    public ResourceFacadeImpl(ResourceService resourceService, ResourceMapper resourceMapper) {
        this.resourceService = resourceService;
        this.resourceMapper = resourceMapper;
    }

    @Override
    public ResourcesResponse getListAllResources() {
        List<Resource> listAllResources = resourceService.getListAllResources();
        return new ResourcesResponse(listAllResources.stream()
                .map(resourceMapper::toDTO)
                .collect(Collectors.toList()));
    }

    // Возвращать только идентификатор
    @Override
    public AddResourceResponse addResource(AddResourceRequest request) {
        Resource resource = Resource.create(request.getName(), request.getResourceType(), request.getUnits());
        Resource savedResource = resourceService.save(resource);
        return new AddResourceResponse(
                StatusResponse.SUCCESS,
                String.format("Добавление ресурса выполнено успешно 'id: %d'.", savedResource.id().orElseThrow()),
                resourceMapper.toDTO(savedResource));
    }

    @Override
    public UpdateResourceResponse updateResource(Long id, UpdateResourceRequest request) {
        Optional<Resource> resourceCandidate = resourceService.findById(id);
        if (resourceCandidate.isEmpty()) {
            return new UpdateResourceResponse(StatusResponse.ERROR, String.format("Ресурс не найден 'id: %d'.", id));
        }
        Resource resource = resourceCandidate.get();
        resource = resource
                .updateName(request.getName())
                .updateType(request.getType())
                .updateUnits(request.getUnits());
        resourceService.save(resource);
        return new UpdateResourceResponse(
                StatusResponse.SUCCESS,
                String.format("Обновление ресурса выполнено успешно 'id: %d'.", id));
    }

    @Override
    public DeleteResourceResponse deleteResource(Long id) {
        DeleteResourceResponse response;
        if (!resourceService.existsById(id)) {
            response = new DeleteResourceResponse(
                    StatusResponse.ERROR,
                    String.format("Ресурс не найден 'id: %d'.", id));
        } else {
            resourceService.deleteById(id);
            response = new DeleteResourceResponse(
                    StatusResponse.SUCCESS,
                    String.format("Удаление ресурса выполенено успешно 'id: %d'.", id));
        }
        return response;
    }
}
