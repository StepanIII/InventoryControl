package com.example.inventory.control.mapper;

import com.example.inventory.control.api.resources.model.ResourceDto;
import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.domain.models.Resource;
import org.mapstruct.Mapper;

/**
 * Маппер сущности "Ресурсы".
 */
@Mapper(componentModel = "spring")
public abstract class ResourceMapper {

    /**
     * Преобразовать в сущность.
     *
     * @param domainModel преобразоваемая доменная модель.
     * @return сущность.
     */
    public ResourceEntity toEntity(Resource domainModel) {
        return new ResourceEntity(
                domainModel.id().orElse(null),
                domainModel.getName(),
                domainModel.getType(),
                domainModel.getUnit());
    }

    /**
     * Преобразовать в доменную модель.
     *
     * @param entity преобразоваемая сущность.
     * @return доменная модель.
     */
    public Resource toDomainModel(ResourceEntity entity) {
        return new Resource.Builder()
                .setId(entity.getId())
                .setName(entity.getName())
                .setResourceType(entity.getType())
                .setUnits(entity.getUnit())
                .build();
    }

    /**
     * Преобразовать в DTO.
     *
     * @param domainModel преобразоваемая доменная модель.
     * @return dto.
     */
    public ResourceDto toDto(Resource domainModel) {
        ResourceDto dto = new ResourceDto();
        dto.setId(domainModel.id().orElseThrow());
        dto.setName(domainModel.getName());
        dto.setType(domainModel.getType());
        dto.setUnit(domainModel.getUnit());
        return dto;
    }




}
