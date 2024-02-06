package com.example.inventory.control.facades.impl;

import com.example.inventory.control.facades.WriteOffFacade;
import com.example.inventory.control.domain.models.WriteOff;
import com.example.inventory.control.domain.models.WriteOffResourceCount;
import com.example.inventory.control.services.WarehouseService;
import com.example.inventory.control.services.WriteOffService;
import com.example.inventory.control.api.requests.writeOff.UpdateWriteOffRequest;
import com.example.inventory.control.api.responses.StatusResponse;
import com.example.inventory.control.api.warehouse.model.WarehouseBody;
import com.example.inventory.control.api.writeoff.AddWriteOffRequest;
import com.example.inventory.control.api.writeoff.AddWriteOffResponse;
import com.example.inventory.control.api.writeoff.UpdateWriteOffResponse;
import com.example.inventory.control.api.writeoff.WriteOffResourceCountRequest;
import com.example.inventory.control.api.writeoff.WriteOffResourceCountResponse;
import com.example.inventory.control.api.writeoff.WriteOffResourcesResponse;
import com.example.inventory.control.api.writeoff.model.WriteOffBody;
import com.example.inventory.control.api.writeoff.WriteOffsResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WriteOffFacadeImpl implements WriteOffFacade {

    private final WarehouseService warehouseService;
    private final WriteOffService writeOffService;

    public WriteOffFacadeImpl(WarehouseService warehouseService, WriteOffService writeOffService) {
        this.warehouseService = warehouseService;
        this.writeOffService = writeOffService;
    }

    @Override
    public WriteOffsResponse getListAllWriteOff() {
        List<WriteOffBody> writeOffResponseList = writeOffService
                .getListAllWriteOff().stream()
                .map(w -> new WriteOffBody(w.id().orElseThrow(), w.getCreatedTime(), w.getWarehouse().getName()))
                .toList();
        return new WriteOffsResponse(writeOffResponseList);
    }

    @Override
    public WriteOffResourcesResponse getWriteOffById(Long id) {
        Optional<WriteOff> writeOffCandidate = writeOffService.find(id);
        if (writeOffCandidate.isEmpty()) {
            return new WriteOffResourcesResponse(
                    StatusResponse.ERROR,
                    String.format("Списание с идентификатором 'id: %d' не найдено", id));
        }
        WriteOff writeOff = writeOffCandidate.get();
        com.example.inventory.control.domain.models.Warehouse warehouse = writeOff.getWarehouse();

        WarehouseBody warehouseResponse = new WarehouseBody(warehouse.id().orElseThrow(), warehouse.getName());
        List<WriteOffResourceCountResponse> resourcesResponse = writeOff.getWriteOffResourceCounts().stream()
                .map(r -> new WriteOffResourceCountResponse(
                        r.id().orElseThrow(),
                        r.getResourceId(),
                        r.getName(),
                        r.getCount()))
                .toList();

        return new WriteOffResourcesResponse(
                StatusResponse.SUCCESS,
                String.format("Списание с идентификатором 'id: %d' найдено", id),
                writeOff.id().orElseThrow(),
                writeOff.getCreatedTime(),
                warehouseResponse,
                resourcesResponse);
    }

    @Override
    @Transactional
    public AddWriteOffResponse addWriteOff(AddWriteOffRequest request) {
        Optional<com.example.inventory.control.domain.models.Warehouse> warehouseCandidate = warehouseService.getWarehouseById(request.getWarehouseId());
        if (warehouseCandidate.isEmpty()) {
            return new AddWriteOffResponse(StatusResponse.ERROR, String.format("Место хранения с идентификатором = %d не найдено.", request.getWarehouseId()));
        }
        com.example.inventory.control.domain.models.Warehouse warehouse = warehouseCandidate.get();
        List<Long> writeOffResourceIds = request.getResources().stream().map(WriteOffResourceCountRequest::getResourceId).toList();
        if (!warehouse.hasAllResources(writeOffResourceIds)) {
            return new AddWriteOffResponse(StatusResponse.ERROR, "На складе нет таких ресурсов.");
        }
        for (WriteOffResourceCountRequest writeOffResourceCountRequest : request.getResources()) {
            if (!warehouse.hasCountResources(writeOffResourceCountRequest.getResourceId(), writeOffResourceCountRequest.getCount())) {
                return new AddWriteOffResponse(StatusResponse.ERROR, "На складе нет такого количества ресурсов.");
            }
        }
        for (WriteOffResourceCountRequest writeOffResourceCountRequest : request.getResources()) {
            warehouse.writeOffResources(writeOffResourceCountRequest.getResourceId(), writeOffResourceCountRequest.getCount());
        }
        warehouseService.update(warehouse);
        List<WriteOffResourceCount> writeOffResourceCounts = request.getResources().stream()
                .map(r -> WriteOffResourceCount.create(r.getResourceId(), r.getCount()))
                .toList();
        WriteOff writeOff = WriteOff.create(warehouse, writeOffResourceCounts);
        writeOffService.save(writeOff);
        return new AddWriteOffResponse(StatusResponse.SUCCESS, "Списание создано.");
    }

    @Override // Написать тесты
    public UpdateWriteOffResponse updateWriteOff(Long id, UpdateWriteOffRequest request) {
        Optional<WriteOff> writeOffCandidate = writeOffService.find(id);
        if (writeOffCandidate.isEmpty()) {
            return new UpdateWriteOffResponse(
                    StatusResponse.ERROR,
                    String.format("Списание с идентификатором 'id: %d' не найдено", id));
        }
        Optional<com.example.inventory.control.domain.models.Warehouse> warehouseCandidate = warehouseService.getWarehouseById(request.getWarehouseId());
        if (warehouseCandidate.isEmpty()) {
            return new UpdateWriteOffResponse(StatusResponse.ERROR,
                    String.format("Место хранения с идентификатором = %d не найдено.", request.getWarehouseId()));
        }
        com.example.inventory.control.domain.models.Warehouse warehouse = warehouseCandidate.get();
        List<Long> writeOffResourceIds = request.getResources().stream().map(WriteOffResourceCountRequest::getResourceId).toList();
        if (!warehouse.hasAllResources(writeOffResourceIds)) {
            return new UpdateWriteOffResponse(StatusResponse.ERROR, "На складе нет таких ресурсов.");
        }

        WriteOff writeOff = writeOffCandidate.get();
        for (WriteOffResourceCountRequest writeOffResourceCountRequest : request.getResources()) {
            WriteOffResourceCount resourceCount = writeOff.getByResourceId(writeOffResourceCountRequest.getResourceId());
            int newWriteOffCount;
            if (resourceCount.getCount() > writeOffResourceCountRequest.getCount()) {
                newWriteOffCount = resourceCount.getCount() - writeOffResourceCountRequest.getCount();
            } else {
                newWriteOffCount = writeOffResourceCountRequest.getCount() - resourceCount.getCount();
            }
            if (!warehouse.hasCountResources(writeOffResourceCountRequest.getResourceId(), writeOffResourceCountRequest.getCount())) {
                return new UpdateWriteOffResponse(StatusResponse.ERROR, "На складе нет такого количества ресурсов.");
            }
            warehouse.writeOffResources(writeOffResourceCountRequest.getResourceId(), newWriteOffCount);
            writeOff = writeOff.updateResourceCount(writeOffResourceCountRequest.getResourceId(), writeOffResourceCountRequest.getCount());
        }
        writeOff = writeOffService.update(writeOff);
        return new UpdateWriteOffResponse(StatusResponse.SUCCESS,
                String.format("Списание обновлено успешно 'id: %d'.", writeOff.id().orElseThrow()));
    }

}
