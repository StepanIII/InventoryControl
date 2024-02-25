package com.example.inventory.control.mapper;

import com.example.inventory.control.api.resource.operation.move.model.MoveResponseBodyModel;
import com.example.inventory.control.api.resource.operation.move.model.MoveWithResourcesResponseBodyModel;
import com.example.inventory.control.domain.models.Move;
import com.example.inventory.control.entities.MoveEntity;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Маппер для сущности "Перемещения".
 */
@Mapper(componentModel = "spring")
public class MoveMapper {

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private MoveResourceMapper moveResourceMapper;

    public MoveEntity toEntity(Move domainModel) {
        MoveEntity moveEntity = new MoveEntity(
                domainModel.id().orElse(null),
                warehouseMapper.toEntity(domainModel.getFromWarehouse()),
                warehouseMapper.toEntity(domainModel.getToWarehouse()));
        moveEntity.getResources().clear();
        moveEntity.getResources().addAll(moveResourceMapper.toEntity(domainModel.getResources(), moveEntity));
        return moveEntity;
    }

    public Move toDomainModel(MoveEntity entity) {
        return new Move(
                entity.getId(),
                entity.getCreatedTime(),
                warehouseMapper.toDomain(entity.getFromWarehouse()),
                warehouseMapper.toDomain(entity.getToWarehouse()),
                moveResourceMapper.toDomain(entity.getResources())
        );
    }

    public List<Move> toDomainModel(List<MoveEntity> entities) {
        return entities.stream().map(this::toDomainModel).toList();
    }

    public MoveResponseBodyModel toMoveResponseBodyModel(Move domainModel) {
        MoveResponseBodyModel responseBodyModel = new MoveResponseBodyModel();
        responseBodyModel.setId(domainModel.id().orElseThrow());
        responseBodyModel.setCreatedTime(domainModel.getCreatedTime());
        responseBodyModel.setFromWarehouseName(domainModel.getFromWarehouse().getName());
        responseBodyModel.setToWarehouseName(domainModel.getToWarehouse().getName());
        return responseBodyModel;
    }

    public MoveWithResourcesResponseBodyModel toMoveWithResourcesResponseBodyModel(Move domainModel) {
        MoveWithResourcesResponseBodyModel responseBodyModel = new MoveWithResourcesResponseBodyModel();
        responseBodyModel.setId(domainModel.id().orElseThrow());
        responseBodyModel.setCreatedTime(domainModel.getCreatedTime());
        responseBodyModel.setFromWarehouseName(domainModel.getFromWarehouse().getName());
        responseBodyModel.setToWarehouseName(domainModel.getToWarehouse().getName());
        responseBodyModel.setResources(moveResourceMapper.toMoveResourceResponseBodyModel(domainModel.getResources(), domainModel.getFromWarehouse(), domainModel.getToWarehouse()));
        return responseBodyModel;
    }

}
