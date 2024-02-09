package com.example.inventory.control.mapper;

import com.example.inventory.control.api.resource.operation.acceptance.model.AcceptWithResourcesBodyModel;
import com.example.inventory.control.api.resource.operation.ResourceCountResponseBodyModel;
import com.example.inventory.control.api.resource.operation.acceptance.model.AcceptResponseBodyModel;
import com.example.inventory.control.api.resource.operation.issue.model.IssueResponseBodyModel;
import com.example.inventory.control.api.resource.operation.issue.model.IssueWithResourcesBodyModel;
import com.example.inventory.control.domain.models.Client;
import com.example.inventory.control.domain.models.ResourceOperation;
import com.example.inventory.control.domain.models.Warehouse;
import com.example.inventory.control.entities.ResourceCountEntity;
import com.example.inventory.control.entities.ResourceOperationEntity;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Маппер для сущности "Приемки".
 */
@Mapper(componentModel = "spring")
public abstract class ResourceOperationMapper {

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private ResourceCountMapper resourceCountMapper;

    /**
     * Преобразовать в сущность.
     *
     * @param domainModel преобразоваемая доменная модель.
     * @return сущность.
     */
    public ResourceOperationEntity toEntity(ResourceOperation domainModel) {
        ResourceOperationEntity entity = new ResourceOperationEntity(
                domainModel.id().orElse(null),
                domainModel.getType(),
                warehouseMapper.toEntity(domainModel.getWarehouse()),
                clientMapper.toEntity(domainModel.getClient()));

        List<ResourceCountEntity> resources = domainModel.getResources().stream()
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
    public ResourceOperation toDomain(ResourceOperationEntity entity) {
        return new ResourceOperation(
                entity.getId(),
                entity.getCreatedTime(),
                entity.getType(),
                warehouseMapper.toDomain(entity.getWarehouse()),
                clientMapper.toDomain(entity.getClient()),
                entity.getResourceCounts().stream().map(resourceCountMapper::toDomain).toList());
    }

    /**
     * Преобразовать в DTO.
     *
     * @param domainModel преобразоваемая доменная модель.
     * @return DTO.
     */
    public AcceptResponseBodyModel toAcceptResponseBodyModel(ResourceOperation domainModel) {
        AcceptResponseBodyModel responseBodyModel = new AcceptResponseBodyModel();
        responseBodyModel.setId(domainModel.id().orElseThrow());
        responseBodyModel.setCreatedTime(domainModel.getCreatedTime());
        responseBodyModel.setBenefactorFio(domainModel.getClient().getFio());
        responseBodyModel.setWarehouseName(domainModel.getWarehouse().getName());
        return responseBodyModel;
    }

    /**
     *
     * @param domainModel
     * @return
     */
    public AcceptWithResourcesBodyModel acceptWithResourcesBodyModel(ResourceOperation domainModel) {
        Client client = domainModel.getClient();
        Warehouse warehouse = domainModel.getWarehouse();

        AcceptWithResourcesBodyModel responseBodyModel = new AcceptWithResourcesBodyModel();
        responseBodyModel.setId(domainModel.id().orElseThrow());
        responseBodyModel.setCreatedTime(domainModel.getCreatedTime());
        responseBodyModel.setBenefactorFio(client.getFio());
        responseBodyModel.setWarehouseName(warehouse.getName());

        List<ResourceCountResponseBodyModel> resourceCountsResponse = domainModel.getResources().stream()
                .map(resourceCountMapper::toResponse)
                .toList();
        responseBodyModel.setResources(resourceCountsResponse);
        return responseBodyModel;
    }

    /**
     *
     * @param domainModel
     * @return
     */
    public IssueResponseBodyModel toIssueResponseBodyModel(ResourceOperation domainModel) {
        IssueResponseBodyModel responseBodyModel = new IssueResponseBodyModel();
        responseBodyModel.setId(domainModel.id().orElseThrow());
        responseBodyModel.setCreatedTime(domainModel.getCreatedTime());
        responseBodyModel.setBeneficiaryFio(domainModel.getClient().getFio());
        responseBodyModel.setWarehouseName(domainModel.getWarehouse().getName());
        return responseBodyModel;
    }

    public IssueWithResourcesBodyModel toIssueWithResourcesBodyModel(ResourceOperation domainModel) {
        Client client = domainModel.getClient();
        Warehouse warehouse = domainModel.getWarehouse();

        IssueWithResourcesBodyModel responseBodyModel = new IssueWithResourcesBodyModel();
        responseBodyModel.setId(domainModel.id().orElseThrow());
        responseBodyModel.setCreatedTime(domainModel.getCreatedTime());
        responseBodyModel.setBeneficiaryFio(client.getFio());
        responseBodyModel.setWarehouseName(warehouse.getName());

        List<ResourceCountResponseBodyModel> resourceCountsResponse = domainModel.getResources().stream()
                .map(resourceCountMapper::toResponse)
                .toList();
        responseBodyModel.setResources(resourceCountsResponse);
        return responseBodyModel;
    }

}
