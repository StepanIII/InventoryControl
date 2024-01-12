package com.example.inventory.control.services.mapper;

import com.example.inventory.control.entities.ResourceCountEntity;
import com.example.inventory.control.models.ResourceCount;
import com.example.inventory.control.repositories.ResourceRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ResourceCountMapper {

    @Autowired
    private ResourceRepository resourceRepository;

    public ResourceCountEntity toEntity(ResourceCount domain) {
        ResourceCountEntity entity = new ResourceCountEntity();
        entity.setId(domain.id().orElse(null));
        entity.setResource(resourceRepository.findById(domain.getResourceId()).orElseThrow()); // Оптимизировать
        entity.setCount(domain.getCount());
        return entity;
    }

    public ResourceCount toDomain(ResourceCountEntity entity) {
        return new ResourceCount(entity.getId(), entity.getResource().getId(), entity.getCount());
    }
}
