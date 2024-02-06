package com.example.inventory.control.mapper;

import com.example.inventory.control.api.remaining.model.RemainBodyResponse;
import com.example.inventory.control.entities.RemainingEntity;
import com.example.inventory.control.domain.models.Remain;
import com.example.inventory.control.repositories.ResourceRepository;
import com.example.inventory.control.repositories.WarehouseRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Маппер для сущности "Остатки".
 */
@Mapper(componentModel = "spring")
public abstract class RemainingMapper {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

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

    /**
     *
     * @return
     * @param remain
     */
    public RemainingEntity toEntity(Remain remain) {
        RemainingEntity entity = new RemainingEntity();
        entity.setId(remain.id().orElse(null));
        entity.setCount(remain.getCount());
        entity.setResource(resourceRepository.findById(remain.getResourceId()).orElseThrow());
        entity.setWarehouse(warehouseRepository.findByName(remain.getWarehouseName()).orElseThrow());
        return entity;
    }

    /**
     *
     * @param domainModel
     * @return
     */
    public RemainBodyResponse toBodyResponse(Remain domainModel) {
        RemainBodyResponse response = new RemainBodyResponse();
        response.setResourceId(domainModel.getResourceId());
        response.setResourceName(domainModel.getResourceName());
        response.setWarehouseName(domainModel.getWarehouseName());
        response.setCount(domainModel.getCount());
        return response;
    }


}
