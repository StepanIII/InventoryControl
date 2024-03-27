package com.example.inventory.control.facades.impl;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.warehouse.RemainsResponseBody;
import com.example.inventory.control.api.warehouse.WarehouseRequest;
import com.example.inventory.control.api.warehouse.WarehouseResponseBody;
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

    @Override
    public BaseResponseBody addWarehouse(WarehouseRequest request) {
        if (warehouseService.existsByName(request.getName())) {
            BaseResponseBody responseBody = new BaseResponseBody();
            responseBody.setStatus(StatusResponse.WAREHOUSE_EXISTS);
            responseBody.setDescription(String.format("Склад с именем '%s' уже существует.", request.getName()));
            return responseBody;
        }
        Warehouse warehouse = Warehouse.create(request.getName());
        warehouse = warehouseService.save(warehouse);
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setStatus(StatusResponse.SUCCESS);
        responseBody.setDescription(String.format("Склад добавлен успешно. %d.", warehouse.id().orElseThrow()));
        return responseBody;
    }

    @Override
    public BaseResponseBody deleteWarehouse(Long id) {
        if (!warehouseService.existsById(id)) {
            BaseResponseBody responseBody = new BaseResponseBody();
            responseBody.setStatus(StatusResponse.WAREHOUSE_NOT_FOUND);
            responseBody.setDescription(String.format("Склад с идентификатором '%d' отсутствует.", id));
            return responseBody;
        }
        warehouseService.deleteById(id);
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setStatus(StatusResponse.SUCCESS);
        responseBody.setDescription(String.format("Склад удален успешно. %d.", id));
        return responseBody;
    }

    @Override
    public WarehouseResponseBody getWarehouseById(Long id) {
        Optional<Warehouse> warehouseCandidate = warehouseService.getWarehouseById(id);
        if (warehouseCandidate.isEmpty()) {
            WarehouseResponseBody responseBody = new WarehouseResponseBody();
            responseBody.setStatus(StatusResponse.WAREHOUSE_NOT_FOUND);
            responseBody.setDescription(String.format("Склад с идентификатором %d отсутствует.", id));
            return responseBody;
        }
        Warehouse warehouse = warehouseCandidate.get();
        WarehouseBody warehouseResponseBody = new WarehouseBody();
        warehouseResponseBody.setId(warehouse.id().orElseThrow());
        warehouseResponseBody.setName(warehouse.getName());

        WarehouseResponseBody responseBody = new WarehouseResponseBody();
        responseBody.setStatus(StatusResponse.SUCCESS);
        responseBody.setDescription(String.format("Склад с идентификатором %d найден.", id));
        responseBody.setWarehouse(warehouseResponseBody);
        return responseBody;
    }

    @Override
    public BaseResponseBody updateWarehouse(Long id, WarehouseRequest request) {
        Optional<Warehouse> warehouseCandidate = warehouseService.getWarehouseById(id);
        if (warehouseCandidate.isEmpty()) {
            WarehouseResponseBody responseBody = new WarehouseResponseBody();
            responseBody.setStatus(StatusResponse.WAREHOUSE_NOT_FOUND);
            responseBody.setDescription(String.format("Склад с идентификатором %d отсутствует.", id));
            return responseBody;
        }
        if (warehouseService.existsByName(request.getName())) {
            BaseResponseBody responseBody = new BaseResponseBody();
            responseBody.setStatus(StatusResponse.WAREHOUSE_EXISTS);
            responseBody.setDescription(String.format("Склад с именем '%s' уже существует.", request.getName()));
            return responseBody;
        }
        Warehouse warehouse = warehouseCandidate.get();
        warehouse = warehouse.updateName(request.getName());
        warehouse = warehouseService.save(warehouse);

        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setStatus(StatusResponse.SUCCESS);
        responseBody.setDescription(String.format("Склад с идентификтаором: %d обновлен.", id));
        return responseBody;
    }

}
