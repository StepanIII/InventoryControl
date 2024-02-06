package com.example.inventory.control.facades.impl;

import com.example.inventory.control.api.BaseResponse;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.writeoff.WriteOffRequest;
import com.example.inventory.control.api.writeoff.WriteOffResourceCountRequest;
import com.example.inventory.control.api.writeoff.WriteOffResourcesResponse;
import com.example.inventory.control.api.writeoff.WriteOffsResponse;
import com.example.inventory.control.api.writeoff.model.WriteOffBody;
import com.example.inventory.control.domain.models.Warehouse;
import com.example.inventory.control.domain.models.WriteOff;
import com.example.inventory.control.domain.models.WriteOffResourceCount;
import com.example.inventory.control.facades.WriteOffFacade;
import com.example.inventory.control.mapper.WriteOffMapper;
import com.example.inventory.control.services.WarehouseService;
import com.example.inventory.control.services.WriteOffService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WriteOffFacadeImpl implements WriteOffFacade {

    private final WarehouseService warehouseService;
    private final WriteOffService writeOffService;
    private final WriteOffMapper writeOffMapper;

    public WriteOffFacadeImpl(WarehouseService warehouseService, WriteOffService writeOffService,
                              WriteOffMapper writeOffMapper) {
        this.warehouseService = warehouseService;
        this.writeOffService = writeOffService;
        this.writeOffMapper = writeOffMapper;
    }

    @Override
    public WriteOffsResponse getListAllWriteOff() {
        List<WriteOffBody> writeOffResponseList = writeOffService.getListAllWriteOff().stream()
                .map(writeOffMapper::toWriteOffBody)
                .toList();
        WriteOffsResponse response = new WriteOffsResponse();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Списания получены успешно. Количество: %d.", writeOffResponseList.size()));
        response.setWriteOffs(writeOffResponseList);
        return response;
    }

    @Override
    public WriteOffResourcesResponse getWriteOffById(Long id) {
        Optional<WriteOff> writeOffCandidate = writeOffService.find(id);
        if (writeOffCandidate.isEmpty()) {
            WriteOffResourcesResponse response = new WriteOffResourcesResponse();
            response.setStatus(StatusResponse.WRITE_OFF_NOT_FOUND);
            response.setDescription(String.format("Списание с идентификатором 'id: %d' не найдено", id));
            return response;
        }
        WriteOffResourcesResponse response = new WriteOffResourcesResponse();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Списание с идентификатором 'id: %d' найдено", id));
        response.setWriteOffResources(writeOffMapper.toWriteOffResourcesBody(writeOffCandidate.get()));
        return response;
    }

    @Override
    @Transactional
    public BaseResponse addWriteOff(WriteOffRequest request) {
        Optional<Warehouse> warehouseCandidate = warehouseService.getWarehouseById(request.getWarehouseId());
        if (warehouseCandidate.isEmpty()) {
            BaseResponse response = new BaseResponse();
            response.setStatus(StatusResponse.WAREHOUSE_NOT_FOUND);
            response.setDescription(String.format("Место хранения с идентификатором = %d не найдено.", request.getWarehouseId()));
            return response;
        }
        Warehouse warehouse = warehouseCandidate.get();
        List<Long> writeOffResourceIds = request.getResources().stream()
                .map(WriteOffResourceCountRequest::getResourceId)
                .toList();
        if (!warehouse.hasAllResources(writeOffResourceIds)) {
            BaseResponse response = new BaseResponse();
            response.setStatus(StatusResponse.WAREHOUSE_RESOURCES_NOT_FOUND);
            response.setDescription("Переданные ресурсы на складе не найдены.");
            return response;
        }
        for (WriteOffResourceCountRequest writeOffResourceCountRequest : request.getResources()) {
            if (!warehouse.hasCountResources(writeOffResourceCountRequest.getResourceId(), writeOffResourceCountRequest.getCount())) {
                BaseResponse response = new BaseResponse();
                response.setStatus(StatusResponse.NOT_ENOUGH_RESOURCES);
                response.setDescription("Не достаточное количество ресурсов на складе.");
                return response;
            }
        }
        List<WriteOffResourceCount> writeOffResourceCounts = request.getResources().stream()
                .map(r -> WriteOffResourceCount.create(r.getResourceId(), r.getCount()))
                .toList();
        WriteOff writeOff = WriteOff.create(warehouse, writeOffResourceCounts);
        writeOff = writeOffService.save(writeOff);
        for (WriteOffResourceCountRequest writeOffResourceCountRequest : request.getResources()) {
            warehouse.writeOffResources(writeOffResourceCountRequest.getResourceId(), writeOffResourceCountRequest.getCount());
        }
        warehouseService.save(warehouse);
        BaseResponse response = new BaseResponse();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Списание создано. id: %d.", writeOff.id().orElseThrow()));
        return response;
    }

//    @Override // Написать тесты
//    public UpdateWriteOffResponse updateWriteOff(Long id, UpdateWriteOffRequest request) {
//        Optional<WriteOff> writeOffCandidate = writeOffService.find(id);
//        if (writeOffCandidate.isEmpty()) {
//            return new UpdateWriteOffResponse(
//                    StatusResponse.ERROR,
//                    String.format("Списание с идентификатором 'id: %d' не найдено", id));
//        }
//        Optional<com.example.inventory.control.domain.models.Warehouse> warehouseCandidate = warehouseService.getWarehouseById(request.getWarehouseId());
//        if (warehouseCandidate.isEmpty()) {
//            return new UpdateWriteOffResponse(StatusResponse.ERROR,
//                    String.format("Место хранения с идентификатором = %d не найдено.", request.getWarehouseId()));
//        }
//        com.example.inventory.control.domain.models.Warehouse warehouse = warehouseCandidate.get();
//        List<Long> writeOffResourceIds = request.getResources().stream().map(WriteOffResourceCountRequest::getResourceId).toList();
//        if (!warehouse.hasAllResources(writeOffResourceIds)) {
//            return new UpdateWriteOffResponse(StatusResponse.ERROR, "На складе нет таких ресурсов.");
//        }
//
//        WriteOff writeOff = writeOffCandidate.get();
//        for (WriteOffResourceCountRequest writeOffResourceCountRequest : request.getResources()) {
//            WriteOffResourceCount resourceCount = writeOff.getByResourceId(writeOffResourceCountRequest.getResourceId());
//            int newWriteOffCount;
//            if (resourceCount.getCount() > writeOffResourceCountRequest.getCount()) {
//                newWriteOffCount = resourceCount.getCount() - writeOffResourceCountRequest.getCount();
//            } else {
//                newWriteOffCount = writeOffResourceCountRequest.getCount() - resourceCount.getCount();
//            }
//            if (!warehouse.hasCountResources(writeOffResourceCountRequest.getResourceId(), writeOffResourceCountRequest.getCount())) {
//                return new UpdateWriteOffResponse(StatusResponse.ERROR, "На складе нет такого количества ресурсов.");
//            }
//            warehouse.writeOffResources(writeOffResourceCountRequest.getResourceId(), newWriteOffCount);
//            writeOff = writeOff.updateResourceCount(writeOffResourceCountRequest.getResourceId(), writeOffResourceCountRequest.getCount());
//        }
//        writeOff = writeOffService.update(writeOff);
//        return new UpdateWriteOffResponse(StatusResponse.SUCCESS,
//                String.format("Списание обновлено успешно 'id: %d'.", writeOff.id().orElseThrow()));
//    }

}
