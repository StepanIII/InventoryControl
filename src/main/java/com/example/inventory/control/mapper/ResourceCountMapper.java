package com.example.inventory.control.mapper;

import com.example.inventory.control.api.acceptance.model.ResourceCountBody;
import com.example.inventory.control.entities.AcceptResourceCountEntity;
import com.example.inventory.control.domain.models.AcceptResourceCount;
import com.example.inventory.control.repositories.ResourceRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ResourceCountMapper {

    @Autowired
    private ResourceRepository resourceRepository;

    public AcceptResourceCountEntity toEntity(AcceptResourceCount domain) {
        AcceptResourceCountEntity entity = new AcceptResourceCountEntity();
        entity.setId(domain.id().orElse(null));
        entity.setResource(resourceRepository.findById(domain.getResourceId()).orElseThrow()); // Оптимизировать
        entity.setCount(domain.getCount());
        return entity;
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
