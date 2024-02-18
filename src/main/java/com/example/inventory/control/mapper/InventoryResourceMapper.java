package com.example.inventory.control.mapper;

import com.example.inventory.control.api.resource.operation.inventory.model.InventoryResourceResponseBodyModel;
import com.example.inventory.control.domain.models.InventoryResource;
import com.example.inventory.control.domain.models.ResourceCount;
import com.example.inventory.control.entities.InventoryEntity;
import com.example.inventory.control.entities.InventoryResourceEntity;
import com.example.inventory.control.entities.ResourceCountEntity;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.repositories.ResourceRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class InventoryResourceMapper {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceMapper resourceMapper;

    public InventoryResourceEntity toEntity(InventoryResource domain, InventoryEntity inventoryEntity) {
        Long resourceId = domain.getResource().id().orElseThrow();
        ResourceEntity resourceEntity = resourceRepository.findById(resourceId).orElseThrow();
        return new InventoryResourceEntity(
                domain.id().orElse(null),
                resourceEntity,
                inventoryEntity,
                domain.getActualCount(),
                domain.getEstimatedCount(),
                domain.getDifference()
        );
    }

    public InventoryResource toDomain(InventoryResourceEntity entity) {
        return new InventoryResource(
                entity.getId(),
                resourceMapper.toDomainModel(entity.getResource()),
                entity.getActualCount(),
                entity.getEstimatedCount(),
                entity.getDifference());
    }

    public InventoryResourceResponseBodyModel toInventoryResourceResponseBodyModel(InventoryResource resource) {
        InventoryResourceResponseBodyModel responseBodyModel = new InventoryResourceResponseBodyModel();
        responseBodyModel.setId(resource.id().orElseThrow());
        responseBodyModel.setName(resource.getResource().getName());
        responseBodyModel.setActualCount(resource.getActualCount());
        responseBodyModel.setEstimatedCount(resource.getEstimatedCount());
        responseBodyModel.setDifference(resource.getDifference());
        return responseBodyModel;
    }
}
