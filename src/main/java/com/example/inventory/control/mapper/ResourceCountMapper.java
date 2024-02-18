package com.example.inventory.control.mapper;

import com.example.inventory.control.api.resource.operation.ResourceCountResponseBodyModel;
import com.example.inventory.control.domain.models.InventoryResource;
import com.example.inventory.control.entities.InventoryResourceEntity;
import com.example.inventory.control.entities.ResourceCountEntity;
import com.example.inventory.control.domain.models.ResourceCount;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.ResourceOperationEntity;
import com.example.inventory.control.repositories.ResourceRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ResourceCountMapper {

    @Autowired
    private ResourceRepository resourceRepository;

    public ResourceCountEntity toEntity(ResourceCount domain, ResourceOperationEntity resourceOperationEntity) {
        return new ResourceCountEntity(
                domain.id().orElse(null),
                resourceRepository.findById(domain.getResourceId()).orElseThrow(),
                domain.getCount(),
                resourceOperationEntity);
    }

    public ResourceCount toDomain(ResourceCountEntity entity) {
        return new ResourceCount(entity.getId(), entity.getResource().getId(), entity.getResource().getName(), entity.getCount());
    }

    public ResourceCountResponseBodyModel toResponse(ResourceCount domainModel) {
        ResourceCountResponseBodyModel response = new ResourceCountResponseBodyModel();
        response.setId(domainModel.getResourceId());
        response.setName(domainModel.getName());
        response.setCount(domainModel.getCount());
        return response;
    }
}
