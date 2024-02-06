package com.example.inventory.control.mapper;

import com.example.inventory.control.api.warehouse.model.WarehouseBody;
import com.example.inventory.control.entities.RemainingEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.domain.models.Warehouse;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Маппер для сущности "Ресурсы".
 */
@Mapper(componentModel = "spring")
public abstract class WarehouseMapper {

    @Autowired
    private RemainingMapper remainingMapper;

    /**
     * Преобразовать в сущность.
     *
     * @param domainModel преобразоваемая доменная модель
     * @return сущность
     */
    public WarehouseEntity toEntity(Warehouse domainModel) {
        WarehouseEntity entity = new WarehouseEntity();
        entity.setId(domainModel.id().orElse(null));
        entity.setName(domainModel.getName());

        Set<RemainingEntity> remainingEntities = domainModel.getRemains().stream()
                .map(remainingMapper::toEntity)
                .collect(Collectors.toSet());

        entity.getResourceCounts().clear();
        entity.getResourceCounts().addAll(remainingEntities);
        return entity;
    }

    /**
     * Преобразовать в доменную модель.
     *
     * @param entity преобразоваемая сущность
     * @return доменная модель
     */
    public Warehouse toDomain(WarehouseEntity entity) {
        return new Warehouse(entity.getId(), entity.getName(), entity.getResourceCounts().stream().map(remainingMapper::toDomain).collect(Collectors.toSet()));
    }

    /**
     *
     * @param domainModel
     * @return
     */
    public WarehouseBody toBodyResponse(Warehouse domainModel) {
        WarehouseBody body = new WarehouseBody();
        body.setId(domainModel.id().orElseThrow());
        body.setName(domainModel.getName());
        return body;
    }


}
