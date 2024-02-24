package com.example.inventory.control.mapper;

import com.example.inventory.control.api.resource.operation.acceptance.model.AcceptWithResourcesBodyModel;
import com.example.inventory.control.api.resource.operation.ResourceCountResponseBodyModel;
import com.example.inventory.control.api.resource.operation.acceptance.model.AcceptResponseBodyModel;
import com.example.inventory.control.api.resource.operation.capitalization.model.CapitalizationResponseBodyModel;
import com.example.inventory.control.api.resource.operation.capitalization.model.CapitalizationWithCaseResponseBodyModel;
import com.example.inventory.control.api.resource.operation.issue.model.IssueResponseBodyModel;
import com.example.inventory.control.api.resource.operation.issue.model.IssueWithResourcesBodyModel;
import com.example.inventory.control.api.resource.operation.move.model.MoveResponseBodyModel;
import com.example.inventory.control.api.resource.operation.write.off.model.WriteOffResponseBodyModel;
import com.example.inventory.control.api.resource.operation.write.off.model.WriteOffWithCaseResponseBodyModel;
import com.example.inventory.control.domain.models.Client;
import com.example.inventory.control.domain.models.ResourceOperation;
import com.example.inventory.control.domain.models.Warehouse;
import com.example.inventory.control.entities.ClientEntity;
import com.example.inventory.control.entities.ResourceCountEntity;
import com.example.inventory.control.entities.ResourceOperationEntity;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

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
        Optional<Client> clientCandidate = domainModel.client();
        ClientEntity clientEntity = null;
        if (domainModel.client().isPresent()) {
            clientEntity = clientMapper.toEntity(clientCandidate.orElseThrow());
        }

        ResourceOperationEntity entity = new ResourceOperationEntity(
                domainModel.id().orElse(null),
                domainModel.getType(),
                domainModel.description().orElse(null),
                warehouseMapper.toEntity(domainModel.getWarehouse()),
                clientEntity);

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
        Client client = null;
        if (entity.getClient() != null) {
            client = clientMapper.toDomain(entity.getClient());
        }

        return new ResourceOperation(
                entity.getId(),
                entity.getCreatedTime(),
                entity.getType(),
                entity.getDescription(),
                warehouseMapper.toDomain(entity.getWarehouse()),
                client,
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
        responseBodyModel.setBenefactorFio(domainModel.client().orElseThrow().getFio());
        responseBodyModel.setWarehouseName(domainModel.getWarehouse().getName());
        return responseBodyModel;
    }

    /**
     *
     * @param domainModel
     * @return
     */
    public AcceptWithResourcesBodyModel acceptWithResourcesBodyModel(ResourceOperation domainModel) {
        Client client = domainModel.client().orElseThrow();
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
        responseBodyModel.setBeneficiaryFio(domainModel.client().orElseThrow().getFio());
        responseBodyModel.setWarehouseName(domainModel.getWarehouse().getName());
        return responseBodyModel;
    }

    public IssueWithResourcesBodyModel toIssueWithResourcesBodyModel(ResourceOperation domainModel) {
        Client client = domainModel.client().orElseThrow();
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

    /**
     *
     * @param domainModel
     * @return
     */
    public CapitalizationResponseBodyModel toCapitalizationResponseBodyModel(ResourceOperation domainModel) {
        CapitalizationResponseBodyModel responseBodyModel = new CapitalizationResponseBodyModel();
        responseBodyModel.setId(domainModel.id().orElseThrow());
        responseBodyModel.setCreatedTime(domainModel.getCreatedTime());
        responseBodyModel.setWarehouseName(domainModel.getWarehouse().getName());
        return responseBodyModel;
    }

    /**
     *
     * @param domainModel
     * @return
     */
    public CapitalizationWithCaseResponseBodyModel toCapitalizationWithCaseResponseBodyModel(ResourceOperation domainModel) {
        Warehouse warehouse = domainModel.getWarehouse();

        CapitalizationWithCaseResponseBodyModel responseBodyModel = new CapitalizationWithCaseResponseBodyModel();
        responseBodyModel.setId(domainModel.id().orElseThrow());
        responseBodyModel.setCreatedTime(domainModel.getCreatedTime());
        responseBodyModel.setWarehouseName(warehouse.getName());
        responseBodyModel.setDescription(domainModel.description().orElse(null));

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
    public WriteOffResponseBodyModel toWriteOffResponseBodyModel(ResourceOperation domainModel) {
        WriteOffResponseBodyModel responseBodyModel = new WriteOffResponseBodyModel();
        responseBodyModel.setId(domainModel.id().orElseThrow());
        responseBodyModel.setCreatedTime(domainModel.getCreatedTime());
        responseBodyModel.setWarehouseName(domainModel.getWarehouse().getName());
        return responseBodyModel;
    }

    /**
     *
     * @param domainModel
     * @return
     */
    public WriteOffWithCaseResponseBodyModel toWriteOffWithCaseResponseBodyModel(ResourceOperation domainModel) {
        Warehouse warehouse = domainModel.getWarehouse();

        WriteOffWithCaseResponseBodyModel responseBodyModel = new WriteOffWithCaseResponseBodyModel();
        responseBodyModel.setId(domainModel.id().orElseThrow());
        responseBodyModel.setCreatedTime(domainModel.getCreatedTime());
        responseBodyModel.setWarehouseName(warehouse.getName());
        responseBodyModel.setDescription(domainModel.description().orElse(null));

        List<ResourceCountResponseBodyModel> resourceCountsResponse = domainModel.getResources().stream()
                .map(resourceCountMapper::toResponse)
                .toList();
        responseBodyModel.setResources(resourceCountsResponse);
        return responseBodyModel;
    }

}
