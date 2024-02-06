package com.example.inventory.control.mapper;

import com.example.inventory.control.api.writeoff.model.WriteOffBody;
import com.example.inventory.control.api.writeoff.model.WriteOffResourceCountBody;
import com.example.inventory.control.api.writeoff.model.WriteOffResourcesBody;
import com.example.inventory.control.entities.WriteOffEntity;
import com.example.inventory.control.domain.models.WriteOff;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    /**
     * @param domainModel
     * @return
     */
    public WriteOffBody toWriteOffBody(WriteOff domainModel) {
        WriteOffBody body = new WriteOffBody();
        body.setId(domainModel.id().orElseThrow());
        body.setCreatedTime(domainModel.getCreatedTime());
        body.setWarehouseName(domainModel.getWarehouse().getName());
        return body;
    }

    /**
     *
     * @param domainModel
     * @return
     */
    public WriteOffResourcesBody toWriteOffResourcesBody(WriteOff domainModel) {
        WriteOffResourcesBody body = new WriteOffResourcesBody();
        body.setId(domainModel.id().orElseThrow());
        body.setCreatedTime(domainModel.getCreatedTime());
        body.setWarehouse(warehouseMapper.toBodyResponse(domainModel.getWarehouse()));

        List<WriteOffResourceCountBody> resourcesResponseBody = domainModel.getWriteOffResourceCounts().stream()
                .map(writeOffCountMapper::toResponseBody)
                .toList();

        body.setResources(resourcesResponseBody);
        return body;
    }

}
