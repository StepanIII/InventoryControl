package com.example.inventory.control.services.mapper;

import com.example.inventory.control.entities.AcceptanceEntity;
import com.example.inventory.control.entities.RemainingEntity;
import com.example.inventory.control.models.Acceptance;
import com.example.inventory.control.models.Remain;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Маппер для сущности "Остатки".
 */
@Mapper(componentModel = "spring")
public abstract class RemainingMapper {


    /**
     * Преобразовать в доменную модель.
     *
     * @param entity преобразоваемая сущность
     * @return доменная модель
     */
    public Remain toDomain(RemainingEntity entity) {
        return new Remain(
                entity.getId(),
                entity.getResource().getId(),
                entity.getResource().getName(),
                entity.getCount(),
                entity.getWarehouse().getName());
    }
//



}
