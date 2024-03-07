package com.example.inventory.control.facades.impl;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.inventory.AllInventoryResponseBody;
import com.example.inventory.control.api.resource.operation.inventory.InventoryRequestBody;
import com.example.inventory.control.api.resource.operation.inventory.InventoryResourceRequestBody;
import com.example.inventory.control.api.resource.operation.inventory.InventoryResponseBody;
import com.example.inventory.control.api.resource.operation.inventory.model.InventoryResponseBodyModel;
import com.example.inventory.control.domain.models.Inventory;
import com.example.inventory.control.domain.models.InventoryResource;
import com.example.inventory.control.domain.models.Warehouse;
import com.example.inventory.control.facades.InventoryFacade;
import com.example.inventory.control.mapper.InventoryMapper;
import com.example.inventory.control.services.InventoryService;
import com.example.inventory.control.services.ResourceService;
import com.example.inventory.control.services.WarehouseService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryFacadeImpl implements InventoryFacade {

    private final InventoryService inventoryService;
    private final WarehouseService warehouseService;
    private final ResourceService resourceService;
    private final InventoryMapper inventoryMapper;

    public InventoryFacadeImpl(InventoryService inventoryService, WarehouseService warehouseService,
                               ResourceService resourceService, InventoryMapper inventoryMapper) {
        this.inventoryService = inventoryService;
        this.warehouseService = warehouseService;
        this.resourceService = resourceService;
        this.inventoryMapper = inventoryMapper;
    }

    @Override
    public AllInventoryResponseBody getAllInventory() {
        List<InventoryResponseBodyModel> inventory = inventoryService
                .getAllInventory().stream()
                .map(inventoryMapper::toInventoryResponseBodyModel)
                .toList();
        AllInventoryResponseBody responseBody = new AllInventoryResponseBody();
        responseBody.setStatus(StatusResponse.SUCCESS);
        responseBody.setDescription(String.format("Инвентаризации получены успешно. Количество %d.", inventory.size()));
        responseBody.setInventory(inventory);
        return responseBody;
    }

    @Override
    public InventoryResponseBody getInventoryById(Long id) {
        Optional<Inventory> inventoryCandidate = inventoryService.findInventoryById(id);
        if (inventoryCandidate.isEmpty()) {
            InventoryResponseBody response = new InventoryResponseBody();
            response.setStatus(StatusResponse.INVENTORY_NOT_FOUND);
            response.setDescription(String.format("Инвентаризация с идентификатором 'id: %d' не найдена", id));
            return response;
        }
        InventoryResponseBody response = new InventoryResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Инвентаризация с идентификатором 'id: %d' найдена", id));
        response.setInventory(inventoryMapper.toInventoryWithResourcesBodyModel(inventoryCandidate.get()));
        return response;
    }

    @Override
    public BaseResponseBody addInventory(InventoryRequestBody request) {
        Optional<Warehouse> warehouseCandidate = warehouseService.getWarehouseById(request.getWarehouseId());
        if (warehouseCandidate.isEmpty()) {
            BaseResponseBody response = new BaseResponseBody();
            response.setStatus(StatusResponse.WAREHOUSE_NOT_FOUND);
            response.setDescription(String.format("Место хранения с идентификатором = %d не найдено.", request.getWarehouseId()));
            return response;
        }
        List<Long> requestResourcesIds = request.getResources().stream()
                .map(InventoryResourceRequestBody::getResourceId)
                .toList();
        boolean isExistsAllResources = resourceService.existsAllByIds(requestResourcesIds);
        if (!isExistsAllResources) {
            BaseResponseBody response = new BaseResponseBody();
            response.setStatus(StatusResponse.RESOURCE_NOT_FOUND);
            response.setDescription(String.format(
                    "Не найдены все ресурсы по списку идентификаторов: %s.",
                    StringUtils.collectionToDelimitedString(requestResourcesIds, ";")));
            return response;
        }
        List<InventoryResource> inventoryResources = request.getResources().stream()
                .map(r -> InventoryResource.create(r.getResourceId(), r.getActualCount(), r.getSettlementCount()))
                .toList();
        Inventory inventory = Inventory.create(warehouseCandidate.get(), inventoryResources);
        inventory = inventoryService.save(inventory);
        BaseResponseBody response = new BaseResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Инвентаризация добавлена успешно 'id: %d'.", inventory.id().orElseThrow()));
        return response;
    }

    @Override
    public BaseResponseBody editInventory(Long id, InventoryRequestBody request) {
        Optional<Inventory> inventoryCandidate = inventoryService.findInventoryById(id);
        if (inventoryCandidate.isEmpty()) {
            InventoryResponseBody response = new InventoryResponseBody();
            response.setStatus(StatusResponse.INVENTORY_NOT_FOUND);
            response.setDescription(String.format("Инвентаризация с идентификатором 'id: %d' не найдена", id));
            return response;
        }
        Optional<Warehouse> warehouseCandidate = warehouseService.getWarehouseById(request.getWarehouseId());
        if (warehouseCandidate.isEmpty()) {
            BaseResponseBody response = new BaseResponseBody();
            response.setStatus(StatusResponse.WAREHOUSE_NOT_FOUND);
            response.setDescription(String.format("Место хранения с идентификатором = %d не найдено.", request.getWarehouseId()));
            return response;
        }
        List<Long> requestResourcesIds = request.getResources().stream()
                .map(InventoryResourceRequestBody::getResourceId)
                .toList();
        boolean isExistsAllResources = resourceService.existsAllByIds(requestResourcesIds);
        if (!isExistsAllResources) {
            BaseResponseBody response = new BaseResponseBody();
            response.setStatus(StatusResponse.RESOURCE_NOT_FOUND);
            response.setDescription(String.format(
                    "Не найдены все ресурсы по списку идентификаторов: %s.",
                    StringUtils.collectionToDelimitedString(requestResourcesIds, ";")));
            return response;
        }
        List<InventoryResource> updatedResources = request.getResources().stream()
                .map(r -> InventoryResource.create(r.getResourceId(), r.getActualCount(), r.getSettlementCount()))
                .toList();
        Inventory updatedInventory = inventoryCandidate.get();
        updatedInventory = updatedInventory.updateResources(updatedResources);
        updatedInventory = inventoryService.save(updatedInventory);
        BaseResponseBody response = new BaseResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Инвентаризация обновлена успешно 'id: %d'.", updatedInventory.id().orElseThrow()));
        return response;
    }

    @Override
    public BaseResponseBody deleteById(Long id) {
        if (!inventoryService.existsById(id)) {
            InventoryResponseBody response = new InventoryResponseBody();
            response.setStatus(StatusResponse.INVENTORY_NOT_FOUND);
            response.setDescription(String.format("Инвентаризация с идентификатором 'id: %d' не найдена", id));
            return response;
        }
        inventoryService.deleteById(id);
        BaseResponseBody response = new BaseResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Удаление инвентаризации выполенено успешно 'id: %d'.", id));
        return response;
    }

}
