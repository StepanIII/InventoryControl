package com.example.inventory.control.mapper;

import com.example.inventory.control.api.resource.operation.inventory.InventoryResponseBody;
import com.example.inventory.control.api.resource.operation.inventory.model.InventoryResourceResponseBodyModel;
import com.example.inventory.control.api.resource.operation.inventory.model.InventoryResponseBodyModel;
import com.example.inventory.control.api.resource.operation.inventory.model.InventoryWithResourcesBodyModel;
import com.example.inventory.control.domain.models.Inventory;
import com.example.inventory.control.domain.models.InventoryResource;
import com.example.inventory.control.entities.InventoryEntity;
import com.example.inventory.control.entities.InventoryResourceEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.repositories.WarehouseRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Маппер для сущности "Инвентаризация".
 */
@Mapper(componentModel = "spring")
public abstract class InventoryMapper {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private InventoryResourceMapper inventoryResourceMapper;

    @Autowired
    private WarehouseMapper warehouseMapper;


    /**
     *
     * @param domain
     * @return
     */
    public InventoryEntity toEntity(Inventory domain) {
        Long warehouseId = domain.getWarehouse().id().orElseThrow();
        WarehouseEntity warehouseEntity = warehouseRepository.findById(warehouseId).orElseThrow();

       InventoryEntity inventoryEntity = new InventoryEntity(
               domain.id().orElse(null),
               domain.getCreatedTime(),
               warehouseEntity);

       List<InventoryResourceEntity> resources = domain.getResources().stream()
               .map(r -> inventoryResourceMapper.toEntity(r, inventoryEntity))
               .toList();

       inventoryEntity.getResources().clear();
       inventoryEntity.getResources().addAll(resources);
       return inventoryEntity;
    }

    /**
     *
     * @param entity
     * @return
     */
    public Inventory toDomain(InventoryEntity entity) {
        List<InventoryResource> resources = entity.getResources().stream()
                .map(inventoryResourceMapper::toDomain)
                .toList();
        return new Inventory(
                entity.getId(),
                entity.getCreatedTime(),
                warehouseMapper.toDomain(entity.getWarehouse()),
                resources);
    }

    /**
     *
     * @param inventory
     * @return
     */
    public InventoryResponseBodyModel toInventoryResponseBodyModel(Inventory inventory) {
        InventoryResponseBodyModel responseBodyModel = new InventoryResponseBodyModel();
        responseBodyModel.setId(inventory.id().orElseThrow());
        responseBodyModel.setCreatedTime(inventory.getCreatedTime());
        responseBodyModel.setWarehouseName(inventory.getWarehouse().getName());
        return responseBodyModel;
    }

    /**
     *
     * @param inventory
     * @return
     */
    public InventoryWithResourcesBodyModel toInventoryWithResourcesBodyModel(Inventory inventory) {
        List<InventoryResourceResponseBodyModel> resourcesResponseBodyModel = inventory.getResources().stream()
                .map(inventoryResourceMapper::toInventoryResourceResponseBodyModel)
                .toList();

        InventoryWithResourcesBodyModel resourcesBodyModel = new InventoryWithResourcesBodyModel();
        resourcesBodyModel.setId(inventory.id().orElseThrow());
        resourcesBodyModel.setCreatedTime(inventory.getCreatedTime());
        resourcesBodyModel.setWarehouseName(inventory.getWarehouse().getName());
        resourcesBodyModel.setResources(resourcesResponseBodyModel);
        return resourcesBodyModel;
    }

}
