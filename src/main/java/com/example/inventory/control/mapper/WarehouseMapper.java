package com.example.inventory.control.mapper;

import com.example.inventory.control.api.warehouse.model.WarehouseBody;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.domain.models.Warehouse;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

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
     * @param domain преобразоваемая доменная модель
     * @return сущность
     */
    public WarehouseEntity toEntity(Warehouse domain) {
        WarehouseEntity entity = new WarehouseEntity();
        entity.setId(domain.id().orElse(null));
        entity.setName(domain.getName());
        entity.setResourceCounts(domain.getRemains().stream().map(remainingMapper::toEntity).collect(Collectors.toSet()));
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
    public WarehouseBody toResponse(Warehouse domainModel) {
        WarehouseBody response = new WarehouseBody();
        response.setId(domainModel.id().orElseThrow());
        response.setName(domainModel.getName());
        return response;
    }


}
