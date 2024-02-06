package com.example.inventory.control.mapper;

import com.example.inventory.control.api.acceptance.model.ResourceCountBody;
import com.example.inventory.control.entities.AcceptResourceCountEntity;
import com.example.inventory.control.domain.models.AcceptResourceCount;
import com.example.inventory.control.entities.AcceptanceEntity;
import com.example.inventory.control.repositories.ResourceRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ResourceCountMapper {

    @Autowired
    private ResourceRepository resourceRepository;

    public AcceptResourceCountEntity toEntity(AcceptResourceCount domain, AcceptanceEntity acceptanceEntity) {
        return new AcceptResourceCountEntity(
                domain.id().orElse(null),
                resourceRepository.findById(domain.getResourceId()).orElseThrow(),
                domain.getCount(),
                acceptanceEntity);
    }

    public AcceptResourceCount toDomain(AcceptResourceCountEntity entity) {
        return new AcceptResourceCount(entity.getId(), entity.getResource().getId(), entity.getResource().getName(), entity.getCount());
    }

    public ResourceCountBody toResponse(AcceptResourceCount domainModel) {
        ResourceCountBody response = new ResourceCountBody();
        response.setResourceId(domainModel.getResourceId());
        response.setName(domainModel.getName());
        response.setCount(domainModel.getCount());
        return response;
    }
}
