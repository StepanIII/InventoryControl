package com.example.inventory.control.services.mapper;

import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.models.Resource;
import com.example.inventory.control.ui.models.responses.resource.ResourceResponse;
import org.mapstruct.Mapper;

/**
 * Маппер для сущности "Ресурсы".
 */
@Mapper(componentModel = "spring")
public abstract class ResourceMapper {

    /**
     * Преобразовать в сущность.
     *
     * @param resource преобразоваемая доменная модель
     * @return сущность
     */
    public ResourceEntity toResourceEntity(Resource resource) {
        ResourceEntity resourceEntity = new ResourceEntity();
        resourceEntity.setId(resource.id().orElse(null));
        resourceEntity.setName(resource.getName());
        resourceEntity.setResourceType(resource.getResourceType());
        resourceEntity.setUnits(resource.getUnits());
        return resourceEntity;
    }

    /**
     * Преобразовать в доменную модель.
     *
     * @param resourceEntity преобразоваемая сущность
     * @return доменная модель
     */
    public Resource toResource(ResourceEntity resourceEntity) {
        return new Resource.Builder()
                .setId(resourceEntity.getId())
                .setName(resourceEntity.getName())
                .setResourceType(resourceEntity.getResourceType())
                .setUnits(resourceEntity.getUnits())
                .build();
    }

    public ResourceResponse toDTO(Resource resource) {
        return new ResourceResponse(
                resource.id().orElse(null),
                resource.getName(),
                resource.getResourceType(),
                resource.getUnits());
    }


}
