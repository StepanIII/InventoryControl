package com.example.inventory.control.facades.impl;

import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.inventory.AllInventoryResponseBody;
import com.example.inventory.control.api.resource.operation.inventory.InventoryResponseBody;
import com.example.inventory.control.api.resource.operation.inventory.model.InventoryResponseBodyModel;
import com.example.inventory.control.domain.models.Inventory;
import com.example.inventory.control.facades.InventoryFacade;
import com.example.inventory.control.mapper.InventoryMapper;
import com.example.inventory.control.services.InventoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryFacadeImpl implements InventoryFacade {

    private final InventoryService inventoryService;
    private final InventoryMapper inventoryMapper;

    public InventoryFacadeImpl(InventoryService inventoryService, InventoryMapper inventoryMapper) {
        this.inventoryService = inventoryService;
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

}
