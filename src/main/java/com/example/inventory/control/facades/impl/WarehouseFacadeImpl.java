package com.example.inventory.control.facades.impl;

import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.warehouse.WarehousesResponse;
import com.example.inventory.control.api.warehouse.model.WarehouseBody;
import com.example.inventory.control.facades.WarehouseFacade;
import com.example.inventory.control.mapper.WarehouseMapper;
import com.example.inventory.control.services.WarehouseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseFacadeImpl implements WarehouseFacade {

    private final WarehouseService warehouseService;
    private final WarehouseMapper warehouseMapper;

    public WarehouseFacadeImpl(WarehouseService warehouseService, WarehouseMapper warehouseMapper) {
        this.warehouseService = warehouseService;
        this.warehouseMapper = warehouseMapper;
    }

    @Override
    public WarehousesResponse getAllWarehouses() {
        List<WarehouseBody> warehousesResponse = warehouseService.getAllListWarehouses().stream()
                .map(warehouseMapper::toBodyResponse)
                .toList();
        WarehousesResponse response = new WarehousesResponse();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Все места хранения получены успешно. Количество: %d.", warehousesResponse.size()));
        response.setWarehouses(warehousesResponse);
        return response;
    }

}
