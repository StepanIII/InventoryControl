package com.example.inventory.control.mapper;

import com.example.inventory.control.api.resource.operation.inventory.model.InventoryResourceResponseBodyModel;
import com.example.inventory.control.domain.models.InventoryResource;
import com.example.inventory.control.entities.InventoryEntity;
import com.example.inventory.control.entities.InventoryResourceEntity;
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
        ResourceEntity resourceEntity = resourceRepository.findById(domain.getResourceId()).orElseThrow();
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
                entity.getResource().getId(),
                entity.getResource().getName(),
                entity.getResource().getSize(),
                entity.getResource().getUnit().getValue(),
                entity.getActualCount(),
                entity.getEstimatedCount(),
                entity.getDifference());
    }

    public InventoryResourceResponseBodyModel toInventoryResourceResponseBodyModel(InventoryResource resource) {
        InventoryResourceResponseBodyModel responseBodyModel = new InventoryResourceResponseBodyModel();
        responseBodyModel.setId(resource.id().orElseThrow());
        responseBodyModel.setName(resource.name().orElseThrow());
        responseBodyModel.setSize(resource.size().orElse(null));
        responseBodyModel.setActualCount(resource.getActualCount());
        responseBodyModel.setEstimatedCount(resource.getEstimatedCount());
        responseBodyModel.setDifference(resource.getDifference());
        responseBodyModel.setUnit(resource.unit().orElseThrow());
        return responseBodyModel;
    }
}
