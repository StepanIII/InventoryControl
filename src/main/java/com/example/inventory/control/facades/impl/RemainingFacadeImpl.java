package com.example.inventory.control.facades.impl;

import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.warehouse.RemainsResponseBody;
import com.example.inventory.control.api.warehouse.model.RemainResponseBodyModel;
import com.example.inventory.control.domain.models.Remain;
import com.example.inventory.control.domain.models.Resource;
import com.example.inventory.control.domain.models.Warehouse;
import com.example.inventory.control.facades.RemainingFacade;
import com.example.inventory.control.mapper.RemainMapper;
import com.example.inventory.control.services.RemainingService;
import com.example.inventory.control.api.remain.model.RemainWithWarehouseResponseBodyModel;
import com.example.inventory.control.api.remain.RemainingResponseBody;
import com.example.inventory.control.services.ResourceService;
import com.example.inventory.control.services.WarehouseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public final class RemainingFacadeImpl implements RemainingFacade {

    private final ResourceService resourceService;
    private final RemainingService remainingService;
    private final WarehouseService warehouseService;
    private final RemainMapper remainMapper;

    public RemainingFacadeImpl(ResourceService resourceService, RemainingService remainingService,
                               WarehouseService warehouseService, RemainMapper remainMapper) {
        this.resourceService = resourceService;
        this.remainingService = remainingService;
        this.warehouseService = warehouseService;
        this.remainMapper = remainMapper;
    }

    @Override
    public RemainingResponseBody getAllRemaining() {
        List<RemainWithWarehouseResponseBodyModel> remainingResponseList = remainingService.getListRemaining().stream()
                .map(remainMapper::toRemainWithWarehouseResponseBodyModel)
                .toList();
        RemainingResponseBody response = new RemainingResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Остатки получены успешно. Количество: %d.", remainingResponseList.size()));
        response.setRemaining(remainingResponseList);
        return response;
    }

    @Override
    public RemainsResponseBody getAllRemainsByWarehouseId(Long warehouseId) {
        Optional<Warehouse> warehouseCandidate = warehouseService.getWarehouseById(warehouseId);
        if (warehouseCandidate.isEmpty()) {
            RemainsResponseBody responseBody = new RemainsResponseBody();
            responseBody.setStatus(StatusResponse.WAREHOUSE_NOT_FOUND);
            responseBody.setDescription(String.format("Склад с идентификатором: %d не найден.", warehouseId));
            return responseBody;
        }
        Warehouse warehouse = warehouseCandidate.get();
        Map<Long, Integer> remainsMap = warehouse.getRemains().stream()
                .collect(Collectors.toMap(Remain::getResourceId, Remain::getCount));

        List<Resource> resources = resourceService.getListAllResources();
        List<RemainResponseBodyModel> remainingResponseList = new ArrayList<>(resources.size());
        for (Resource resource : resources) {
            Integer count = remainsMap.get(resource.id().orElseThrow());

            RemainResponseBodyModel responseBodyModel = new RemainResponseBodyModel();
            responseBodyModel.setResourceId(resource.id().orElseThrow());
            responseBodyModel.setSize(resource.size().orElse(null));
            responseBodyModel.setName(resource.getName());
            responseBodyModel.setUnit(resource.getUnit().getValue());
            responseBodyModel.setCount(count != null ? count : 0);

            remainingResponseList.add(responseBodyModel);
        }

        RemainsResponseBody response = new RemainsResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Все остатки склада получены успешно. Количество: %d.", remainingResponseList.size()));
        response.setRemains(remainingResponseList);
        return response;
    }

}
