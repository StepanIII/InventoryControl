package com.example.inventory.control.mapper;

import com.example.inventory.control.api.resource.operation.move.model.MoveResourceResponseBodyModel;
import com.example.inventory.control.domain.models.Move;
import com.example.inventory.control.domain.models.MoveResource;
import com.example.inventory.control.domain.models.Warehouse;
import com.example.inventory.control.entities.MoveEntity;
import com.example.inventory.control.entities.MoveResourceEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
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


    public MoveResourceEntity toEntity(MoveResource domainModel, MoveEntity moveEntity) {
        ResourceEntity resourceEntity = resourceRepository.findById(domainModel.getResourceId()).orElseThrow();
        return new MoveResourceEntity(
                domainModel.id().orElse(null),
                resourceEntity,
                moveEntity,
                domainModel.getCount());
    }

    public List<MoveResourceEntity> toEntity(List<MoveResource> domainModels, MoveEntity moveEntity) {
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

    public List<MoveResource> toDomain(List<MoveResourceEntity> entities) {
        return entities.stream().map(this::toDomain).toList();
    }

    public MoveResourceResponseBodyModel toMoveResourceResponseBodyModel(MoveResource domainModel, Warehouse fromWarehouseEntity, Warehouse toWarehouseEntity) {
        int fromRemainCount;

        if (fromWarehouseEntity.getRemains().isEmpty()) {
            fromRemainCount = 0;
        } else {
            fromRemainCount = fromWarehouseEntity.getRemains().stream()
                    .filter(r -> r.getResourceId().equals(domainModel.getResourceId()))
                    .findAny().orElseThrow()
                    .getCount();
            fromRemainCount = fromRemainCount + domainModel.getCount();
        }

        int toRemainCount;
        if (toWarehouseEntity.getRemains().isEmpty()) {
            toRemainCount = 0;
        } else {
            toRemainCount = toWarehouseEntity.getRemains().stream()
                    .filter(r -> r.getResourceId().equals(domainModel.getResourceId()))
                    .findAny().orElseThrow()
                    .getCount();
            toRemainCount = toRemainCount - domainModel.getCount();
        }

        MoveResourceResponseBodyModel responseBodyModel = new MoveResourceResponseBodyModel();
        responseBodyModel.setId(domainModel.getResourceId());
        responseBodyModel.setCount(domainModel.getCount());
        responseBodyModel.setName(domainModel.name().orElseThrow());
        responseBodyModel.setFromRemainCount(fromRemainCount);
        responseBodyModel.setToRemainCount(toRemainCount);
        responseBodyModel.setUnit(domainModel.unit().orElseThrow());
        return responseBodyModel;
    }

    public List<MoveResourceResponseBodyModel> toMoveResourceResponseBodyModel(List<MoveResource> domainModels, Warehouse fromWarehouseEntity, Warehouse toWarehouseEntity) {
        return domainModels.stream()
                .map(d -> this.toMoveResourceResponseBodyModel(d, fromWarehouseEntity, toWarehouseEntity))
                .toList();
    }
}
