package com.example.inventory.control.mapper;

import com.example.inventory.control.api.acceptance.model.AcceptResourcesBody;
import com.example.inventory.control.api.acceptance.model.ResourceCountBody;
import com.example.inventory.control.api.acceptance.model.AcceptBodyResponse;
import com.example.inventory.control.domain.models.Benefactor;
import com.example.inventory.control.domain.models.Warehouse;
import com.example.inventory.control.entities.AcceptResourceCountEntity;
import com.example.inventory.control.entities.AcceptanceEntity;
import com.example.inventory.control.domain.models.Accept;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Маппер для сущности "Приемки".
 */
@Mapper(componentModel = "spring")
public abstract class AcceptMapper {

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private BenefactorMapper benefactorMapper;

    @Autowired
    private ResourceCountMapper resourceCountMapper;

    /**
     * Преобразовать в сущность.
     *
     * @param domainModel преобразоваемая доменная модель.
     * @return сущность.
     */
    public AcceptanceEntity toEntity(Accept domainModel) {
        AcceptanceEntity entity = new AcceptanceEntity(
                domainModel.id().orElse(null),
                warehouseMapper.toEntity(domainModel.getWarehouse()),
                benefactorMapper.toEntity(domainModel.getBenefactor()));

        List<AcceptResourceCountEntity> resources = domainModel.getResources().stream()
                .map(r -> resourceCountMapper.toEntity(r, entity))
                .toList();

        // В будущем передалать, не добавляя поностью новые ресурсы, а добавляя новые и изменяя старые.
        // Прцесс будет следующий:
        // 1. Удалить реусрсы, которых нет в обновленных.
        // 2. Добавить новые обновленные ресурсы.
        // 3. Обновить старые ресурсы.
        entity.getResourceCounts().clear();
        entity.getResourceCounts().addAll(resources);
        return entity;
    }

    /**
     * Преобразовать в доменную модель.
     *
     * @param entity преобразоваемая сущность.
     * @return доменная модель.
     */
    public Accept toDomain(AcceptanceEntity entity) {
        return new Accept(
                entity.getId(),
                entity.getCreatedTime(),
                warehouseMapper.toDomain(entity.getWarehouse()),
                benefactorMapper.toDomain(entity.getBenefactor()),
                entity.getResourceCounts().stream().map(resourceCountMapper::toDomain).toList());
    }

    /**
     * Преобразовать в DTO.
     *
     * @param domainModel преобразоваемая доменная модель.
     * @return DTO.
     */
    public AcceptBodyResponse toDto(Accept domainModel) {
        AcceptBodyResponse dto = new AcceptBodyResponse();
        dto.setId(domainModel.id().orElseThrow());
        dto.setCreatedTime(domainModel.getCreatedTime());
        dto.setBenefactorFio(domainModel.getBenefactor().getFio());
        dto.setWarehouseName(domainModel.getWarehouse().getName());
        return dto;
    }

    /**
     *
     * @param domainModel
     * @return
     */
    public AcceptResourcesBody toResponse(Accept domainModel) {
        Benefactor benefactor = domainModel.getBenefactor();
        Warehouse warehouse = domainModel.getWarehouse();

        AcceptResourcesBody response = new AcceptResourcesBody();
        response.setId(domainModel.id().orElseThrow());
        response.setCreatedTime(domainModel.getCreatedTime());
        response.setBenefactorFio(benefactor.getFio());
        response.setWarehouseName(warehouse.getName());

        List<ResourceCountBody> resourceCountsResponse = domainModel.getResources().stream()
                .map(resourceCountMapper::toResponse)
                .toList();
        response.setResources(resourceCountsResponse);
        return response;
    }

}
