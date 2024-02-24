package com.example.inventory.control.mapper;

import com.example.inventory.control.domain.models.MoveResource;
import com.example.inventory.control.entities.MoveEntity;
import com.example.inventory.control.entities.MoveResourceEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.repositories.ResourceRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Маппер для сущности "Ресерс перемещения".
 */
@Mapper(componentModel = "spring")
public class MoveResourceMapper {

    @Autowired
    private ResourceRepository resourceRepository;


    MoveResourceEntity toEntity(MoveResource domainModel, MoveEntity moveEntity) {
        ResourceEntity resourceEntity = resourceRepository.findById(domainModel.getResourceId()).orElseThrow();
        return new MoveResourceEntity(
                domainModel.id().orElse(null),
                resourceEntity,
                moveEntity,
                domainModel.getCount());
    }

    List<MoveResourceEntity> toEntity(List<MoveResource> domainModels, MoveEntity moveEntity) {
        return domainModels.stream().map(r -> toEntity(r, moveEntity)).toList();
    }

    MoveResource toDomain(MoveResourceEntity entity) {
        return new MoveResource(
                entity.getId(),
                entity.getResource().getId(),
                entity.getResource().getName(),
                entity.getResource().getUnit().getValue(),
                entity.getCount());
    }

    List<MoveResource> toDomain(List<MoveResourceEntity> entities) {
        return entities.stream().map(this::toDomain).toList();
    }
}
