package com.example.inventory.control.facades.impl;

import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.warehouse.RemainsResponseBody;
import com.example.inventory.control.api.warehouse.WarehousesResponseBody;
import com.example.inventory.control.api.warehouse.model.RemainResponseBodyModel;
import com.example.inventory.control.api.warehouse.model.WarehouseBody;
import com.example.inventory.control.domain.models.Warehouse;
import com.example.inventory.control.facades.WarehouseFacade;
import com.example.inventory.control.mapper.RemainMapper;
import com.example.inventory.control.mapper.WarehouseMapper;
import com.example.inventory.control.services.WarehouseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseFacadeImpl implements WarehouseFacade {

    private final WarehouseService warehouseService;
    private final WarehouseMapper warehouseMapper;
    private final RemainMapper remainMapper;

    public WarehouseFacadeImpl(WarehouseService warehouseService, WarehouseMapper warehouseMapper,
                               RemainMapper remainMapper) {
        this.warehouseService = warehouseService;
        this.warehouseMapper = warehouseMapper;
        this.remainMapper = remainMapper;
    }

    @Override
    public WarehousesResponseBody getAllWarehouses() {
        List<WarehouseBody> warehousesResponse = warehouseService.getAllListWarehouses().stream()
                .map(warehouseMapper::toBodyResponse)
                .toList();
        WarehousesResponseBody response = new WarehousesResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Все места хранения получены успешно. Количество: %d.", warehousesResponse.size()));
        response.setWarehouses(warehousesResponse);
        return response;
    }

    @Override
    public RemainsResponseBody getRemainsByWarehouseId(Long id) {
        Optional<Warehouse> warehouseCandidate = warehouseService.getWarehouseById(id);
        if (warehouseCandidate.isEmpty()) {
            RemainsResponseBody responseBody = new RemainsResponseBody();
            responseBody.setStatus(StatusResponse.WAREHOUSE_NOT_FOUND);
            responseBody.setDescription(String.format("Склад с идентификатором: %d не найден.", id));
            return responseBody;
        }
        Warehouse warehouse = warehouseCandidate.get();
        List<RemainResponseBodyModel> remainsResponseBody = warehouse.getRemains().stream()
                .map(remainMapper::toRemainResponseBodyModel)
                .toList();
        RemainsResponseBody responseBody = new RemainsResponseBody();
        responseBody.setStatus(StatusResponse.SUCCESS);
        responseBody.setDescription(String.format(
                "Остатки ресурсов на складе 'id: %d' получены успешно, количество: %d.",
                warehouse.id().orElseThrow(), remainsResponseBody.size()));
        responseBody.setRemains(remainsResponseBody);
        return responseBody;
    }

}
