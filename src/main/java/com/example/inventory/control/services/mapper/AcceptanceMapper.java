package com.example.inventory.control.services.mapper;

import com.example.inventory.control.entities.AcceptanceEntity;
import com.example.inventory.control.models.Acceptance;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Маппер для сущности "Приемки".
 */
@Mapper(componentModel = "spring")
public abstract class AcceptanceMapper {

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private BenefactorMapper benefactorMapper;

    @Autowired
    private ResourceCountMapper resourceCountMapper;

    /**
     * Преобразовать в сущность.
     *
     * @param domain преобразоваемая доменная модель
     * @return сущность
     */
    public AcceptanceEntity toEntity(Acceptance domain) {
        AcceptanceEntity entity = new AcceptanceEntity();
        entity.setId(domain.id().orElse(null));
        entity.setCreatedTime(domain.getCreatedTime());
        entity.setWarehouse(warehouseMapper.toEntity(domain.getWarehouse()));
        entity.setBenefactor(benefactorMapper.toEntity(domain.getBenefactor()));
        entity.getResourceCounts().addAll(domain.getResources().stream().map(resourceCountMapper::toEntity).toList());
        return entity;
    }

    /**
     * Преобразовать в доменную модель.
     *
     * @param entity преобразоваемая сущность
     * @return доменная модель
     */
    public Acceptance toDomain(AcceptanceEntity entity) {
        return new Acceptance(
                entity.getId(),
                entity.getCreatedTime(),
                warehouseMapper.toDomain(entity.getWarehouse()),
                benefactorMapper.toDomain(entity.getBenefactor()),
                entity.getResourceCounts().stream().map(resourceCountMapper::toDomain).toList());
    }
//
//    public ResourceResponse toDTO(Resource resource) {
//        return new ResourceResponse(
//                resource.id().orElse(null),
//                resource.getName(),
//                resource.getResourceType(),
//                resource.getUnits());
//    }


}
