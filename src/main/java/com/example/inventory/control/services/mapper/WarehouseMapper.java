package com.example.inventory.control.services.mapper;

import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.models.Warehouse;
import org.mapstruct.Mapper;

/**
 * Маппер для сущности "Ресурсы".
 */
@Mapper(componentModel = "spring")
public abstract class WarehouseMapper {

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
        return entity;
    }

    /**
     * Преобразовать в доменную модель.
     *
     * @param entity преобразоваемая сущность
     * @return доменная модель
     */
    public Warehouse toDomain(WarehouseEntity entity) {
        return new Warehouse(entity.getId(), entity.getName());
    }

//    public ResourceResponse toDTO(Resource resource) {
//        return new ResourceResponse(
//                resource.id().orElse(null),
//                resource.getName(),
//                resource.getResourceType(),
//                resource.getUnits());
//    }


}
