package com.example.inventory.control.facades.impl;

import com.example.inventory.control.facades.WarehouseFacade;
import com.example.inventory.control.services.WarehouseService;
import com.example.inventory.control.ui.models.responses.warehouse.WarehouseResponse;
import com.example.inventory.control.ui.models.responses.warehouse.WarehousesResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WarehouseFacadeImpl implements WarehouseFacade {

    private final WarehouseService warehouseService;

    public WarehouseFacadeImpl(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @Override
    public WarehousesResponse getListAllWarehouses() {
        List<WarehouseResponse> warehouseList = warehouseService
                .getAllListWarehouses().stream()
                .map(w -> new WarehouseResponse(w.id().orElseThrow(), w.getName()))
                .collect(Collectors.toList());
        return new WarehousesResponse(warehouseList);
    }

}
