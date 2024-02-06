package com.example.inventory.control.mapper;

import com.example.inventory.control.api.writeoff.model.WriteOffResourceCountBody;
import com.example.inventory.control.entities.WriteOffResourceCountEntity;
import com.example.inventory.control.domain.models.WriteOffResourceCount;
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

    /**
     *
     * @param domainModel
     * @return
     */
    public WriteOffResourceCountBody toResponseBody(WriteOffResourceCount domainModel) {
        WriteOffResourceCountBody body = new WriteOffResourceCountBody();
        body.setResourceId(domainModel.getResourceId());
        body.setName(domainModel.getName());
        body.setCount(domainModel.getCount());
        return body;
    }
}
