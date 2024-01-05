package com.example.inventory.control.services.mapper;

import com.example.inventory.control.entities.BenefactorEntity;
import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.models.Benefactor;
import com.example.inventory.control.models.Warehouse;
import org.mapstruct.Mapper;

/**
 * Маппер для сущности "Благодетели".
 */
@Mapper(componentModel = "spring")
public abstract class BenefactorMapper {

    /**
     * Преобразовать в сущность.
     *
     * @param domain преобразоваемая доменная модель
     * @return сущность
     */
    public BenefactorEntity toEntity(Benefactor domain) {
        BenefactorEntity entity = new BenefactorEntity();
        entity.setId(domain.id().orElse(null));
        entity.setFirstName(domain.getFirstName());
        entity.setMiddleName(domain.getMiddleName());
        entity.setLastName(domain.getLastName());
        return entity;
    }

    /**
     * Преобразовать в доменную модель.
     *
     * @param entity преобразоваемая сущность
     * @return доменная модель
     */
    public Benefactor toDomain(BenefactorEntity entity) {
        return new Benefactor(entity.getId(), entity.getLastName(), entity.getFirstName(), entity.getMiddleName());
    }

//    public ResourceResponse toDTO(Resource resource) {
//        return new ResourceResponse(
//                resource.id().orElse(null),
//                resource.getName(),
//                resource.getResourceType(),
//                resource.getUnits());
//    }


}
