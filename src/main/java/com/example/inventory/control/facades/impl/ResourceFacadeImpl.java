package com.example.inventory.control.facades.impl;

import com.example.inventory.control.api.resources.ResourceRequest;
import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resources.ResourceTypesResponseBody;
import com.example.inventory.control.api.resources.ResourceUnitsResponseBody;
import com.example.inventory.control.api.resources.model.ResourceDto;
import com.example.inventory.control.api.resources.ResourceResponseBody;
import com.example.inventory.control.api.resources.ResourcesResponseBody;
import com.example.inventory.control.domain.models.Resource;
import com.example.inventory.control.enums.ResourceType;
import com.example.inventory.control.enums.Unit;
import com.example.inventory.control.facades.ResourceFacade;
import com.example.inventory.control.mapper.ResourceMapper;
import com.example.inventory.control.services.ResourceService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
    public ResourcesResponseBody getAllResources() {
        List<ResourceDto> resources = resourceService.getListAllResources().stream()
                .map(resourceMapper::toDto)
                .toList();
        ResourcesResponseBody response = new ResourcesResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Ресурсы получены успешно. Количество %d.", resources.size()));
        response.setResources(resources);
        return response;
    }

    @Override
    public ResourceResponseBody getResourceById(Long id) {
        Optional<Resource> resourceCandidate = resourceService.findById(id);
        if (resourceCandidate.isEmpty()) {
            ResourceResponseBody response = new ResourceResponseBody();
            response.setStatus(StatusResponse.RESOURCE_NOT_FOUND);
            response.setDescription(String.format("Ресурс не найден 'id: %d'.", id));
            return response;
        }
        Resource resource = resourceCandidate.get();
        ResourceResponseBody response = new ResourceResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Получение ресурса выполнено успешно 'id: %d'.", id));
        response.setResource(resourceMapper.toDto(resource));
        return response;
    }

    @Override
    public ResourceResponseBody addResource(ResourceRequest request) {
        Resource resource = Resource.create(request.getName(), request.getType(), request.getUnit());
        Resource savedResource = resourceService.save(resource);
        ResourceResponseBody response = new ResourceResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Добавление ресурса выполнено успешно 'id: %d'.", savedResource.id().orElseThrow()));
        response.setResource(resourceMapper.toDto(savedResource));
        return response;
    }

    @Override
    public ResourceResponseBody updateResource(Long id, ResourceRequest request) {
        Optional<Resource> resourceCandidate = resourceService.findById(id);
        if (resourceCandidate.isEmpty()) {
            ResourceResponseBody response = new ResourceResponseBody();
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
        ResourceResponseBody response = new ResourceResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Обновление ресурса выполнено успешно 'id: %d'.", id));
        response.setResource(resourceMapper.toDto(updatedResource));
        return response;
    }

    @Override
    public BaseResponseBody deleteResource(Long id) {
        if (!resourceService.existsById(id)) {
            BaseResponseBody response = new BaseResponseBody();
            response.setStatus(StatusResponse.RESOURCE_NOT_FOUND);
            response.setDescription(String.format("Ресурс не найден 'id: %d'.", id));
            return response;
        }
        resourceService.deleteById(id);
        BaseResponseBody response = new BaseResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Удаление ресурса выполенено успешно 'id: %d'.", id));
        return response;
    }

    @Override
    public ResourceTypesResponseBody getAllResourceTypes() {
        ResourceTypesResponseBody response = new ResourceTypesResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription("Запрос на получение всех типов ресурсов выполнен успешно.");
        List<String> resourceTypes = Arrays.stream(ResourceType.values()).map(ResourceType::getValue).toList();
        response.setResourceTypes(resourceTypes);
        return response;
    }

    @Override
    public ResourceUnitsResponseBody getAllResourceUnits() {
        ResourceUnitsResponseBody response = new ResourceUnitsResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription("Запрос на получение всех единиц измерения ресурсов выполнен успешно.");
        List<String> resourceUnits = Arrays.stream(Unit.values()).map(Unit::getValue).toList();
        response.setResourceUnits(resourceUnits);
        return response;
    }

}
