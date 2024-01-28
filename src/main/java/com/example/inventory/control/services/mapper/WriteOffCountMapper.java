package com.example.inventory.control.services.mapper;

import com.example.inventory.control.entities.AcceptResourceCountEntity;
import com.example.inventory.control.entities.WriteOffResourceCountEntity;
import com.example.inventory.control.models.AcceptResourceCount;
import com.example.inventory.control.models.WriteOffResourceCount;
import com.example.inventory.control.repositories.ResourceRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class WriteOffCountMapper {

    @Autowired
    private ResourceRepository resourceRepository;

    public WriteOffResourceCountEntity toEntity(WriteOffResourceCount domain) {
        WriteOffResourceCountEntity entity = new WriteOffResourceCountEntity();
        entity.setId(domain.id().orElse(null));
        entity.setResource(resourceRepository.findById(domain.getResourceId()).orElseThrow()); // Оптимизировать
        entity.setCount(domain.getCount());
        return entity;
    }

    public WriteOffResourceCount toDomain(WriteOffResourceCountEntity entity) {
        return new WriteOffResourceCount(entity.getId(), entity.getResource().getId(), entity.getResource().getName(), entity.getCount());
    }
}
