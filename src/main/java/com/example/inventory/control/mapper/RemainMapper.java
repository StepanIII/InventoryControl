package com.example.inventory.control.mapper;

import com.example.inventory.control.api.remain.model.RemainWithWarehouseResponseBodyModel;
import com.example.inventory.control.api.warehouse.model.RemainResponseBodyModel;
import com.example.inventory.control.domain.models.Resource;
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
public abstract class RemainMapper {

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
                entity.getResource().getSize(),
                entity.getCount(),
                entity.getResource().getUnit(),
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
    public RemainWithWarehouseResponseBodyModel toRemainWithWarehouseResponseBodyModel(Remain domainModel) {
        RemainWithWarehouseResponseBodyModel responseBodyModel = new RemainWithWarehouseResponseBodyModel();
        responseBodyModel.setResourceId(domainModel.getResourceId());
        responseBodyModel.setName(domainModel.getResourceName());
        responseBodyModel.setSize(domainModel.size().orElse(null));
        responseBodyModel.setWarehouseName(domainModel.getWarehouseName());
        responseBodyModel.setCount(domainModel.getCount());
        responseBodyModel.setUnit(domainModel.getUnit().getValue());
        return responseBodyModel;
    }

    /**
     *
     * @param domainModel
     * @return
     */
    public RemainResponseBodyModel toRemainResponseBodyModel(Remain domainModel) {
        RemainResponseBodyModel responseBodyModel = new RemainResponseBodyModel();
        responseBodyModel.setResourceId(domainModel.getResourceId());
        responseBodyModel.setName(domainModel.getResourceName());
        responseBodyModel.setSize(domainModel.size().orElse(null));
        responseBodyModel.setCount(domainModel.getCount());
        responseBodyModel.setUnit(domainModel.getUnit().getValue());
        return responseBodyModel;
    }



}
