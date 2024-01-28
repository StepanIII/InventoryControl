package com.example.inventory.control.services.mapper;

import com.example.inventory.control.entities.WriteOffEntity;
import com.example.inventory.control.models.WriteOff;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Маппер для сущности "Списания".
 */
@Mapper(componentModel = "spring")
public abstract class WriteOffMapper {

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private WriteOffCountMapper writeOffCountMapper;

    /**
     * Преобразовать в сущность.
     *
     * @param domain преобразоваемая доменная модель
     * @return сущность
     */
    public WriteOffEntity toEntity(WriteOff domain) {
        WriteOffEntity entity = new WriteOffEntity();
        entity.setId(domain.id().orElse(null));
        entity.setCreatedTime(domain.getCreatedTime());
        entity.setWarehouse(warehouseMapper.toEntity(domain.getWarehouse()));
        return entity;
    }

    /**
     * Преобразовать в доменную модель.
     *
     * @param entity преобразоваемая сущность
     * @return доменная модель
     */
    public WriteOff toDomain(WriteOffEntity entity) {
        return new WriteOff(
                entity.getId(),
                entity.getCreatedTime(),
                warehouseMapper.toDomain(entity.getWarehouse()),
                entity.getResourceCounts().stream().map(writeOffCountMapper::toDomain).toList());
    }

}
