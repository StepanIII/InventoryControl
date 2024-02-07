package com.example.inventory.control.facades.impl;

import com.example.inventory.control.api.resources.ResourceRequest;
import com.example.inventory.control.api.BaseResponse;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resources.model.ResourceDto;
import com.example.inventory.control.api.resources.ResourceResponse;
import com.example.inventory.control.api.resources.ResourcesResponse;
import com.example.inventory.control.domain.models.Resource;
import com.example.inventory.control.facades.ResourceFacade;
import com.example.inventory.control.mapper.ResourceMapper;
import com.example.inventory.control.services.ResourceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public final class ResourceFacadeImpl implements ResourceFacade {

    private final ResourceService resourceService;
    private final ResourceMapper resourceMapper;

    public ResourceFacadeImpl(ResourceService resourceService, ResourceMapper resourceMapper) {
        this.resourceService = resourceService;
        this.resourceMapper = resourceMapper;
    }

    @Override
    public ResourcesResponse getAllResources() {
        List<ResourceDto> resources = resourceService.getListAllResources().stream()
                .map(resourceMapper::toDto)
                .toList();
        ResourcesResponse response = new ResourcesResponse();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Ресурсы получены успешно. Количество %d.", resources.size()));
        response.setResources(resources);
        return response;
    }

    @Override
    public ResourceResponse addResource(ResourceRequest request) {
        Resource resource = Resource.create(request.getName(), request.getType(), request.getUnit());
        Resource savedResource = resourceService.save(resource);
        ResourceResponse response = new ResourceResponse();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Добавление ресурса выполнено успешно 'id: %d'.", savedResource.id().orElseThrow()));
        response.setResource(resourceMapper.toDto(savedResource));
        return response;
    }

    @Override
    public ResourceResponse updateResource(Long id, ResourceRequest request) {
        Optional<Resource> resourceCandidate = resourceService.findById(id);
        if (resourceCandidate.isEmpty()) {
            ResourceResponse response = new ResourceResponse();
            response.setStatus(StatusResponse.RESOURCE_NOT_FOUND);
            response.setDescription(String.format("Ресурс не найден 'id: %d'.", id));
            return response;
        }
        Resource resource = resourceCandidate.get();
        resource = resource
                .updateName(request.getName())
                .updateType(request.getType())
                .updateUnits(request.getUnit());
        Resource updatedResource = resourceService.save(resource);
        ResourceResponse response = new ResourceResponse();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Обновление ресурса выполнено успешно 'id: %d'.", id));
        response.setResource(resourceMapper.toDto(updatedResource));
        return response;
    }

    @Override
    public BaseResponse deleteResource(Long id) {
        if (!resourceService.existsById(id)) {
            BaseResponse response = new BaseResponse();
            response.setStatus(StatusResponse.RESOURCE_NOT_FOUND);
            response.setDescription(String.format("Ресурс не найден 'id: %d'.", id));
            return response;
        }
        resourceService.deleteById(id);
        BaseResponse response = new BaseResponse();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Удаление ресурса выполенено успешно 'id: %d'.", id));
        return response;
    }

}
