package com.example.inventory.control.facades.impl;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.move.AllMoveResponseBody;
import com.example.inventory.control.api.resource.operation.move.MoveRequestBody;
import com.example.inventory.control.api.resource.operation.move.MoveResourceRequestBody;
import com.example.inventory.control.api.resource.operation.move.MoveResponseBody;
import com.example.inventory.control.api.resource.operation.move.model.MoveResponseBodyModel;
import com.example.inventory.control.domain.models.Move;
import com.example.inventory.control.domain.models.MoveResource;
import com.example.inventory.control.domain.models.Remain;
import com.example.inventory.control.domain.models.Warehouse;
import com.example.inventory.control.facades.MoveFacade;
import com.example.inventory.control.mapper.MoveMapper;
import com.example.inventory.control.services.MoveService;
import com.example.inventory.control.services.ResourceService;
import com.example.inventory.control.services.WarehouseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class MoveFacadeImpl implements MoveFacade {

    private final MoveService moveService;
    private final WarehouseService warehouseService;
    private final ResourceService resourceService;
    private final MoveMapper moveMapper;

    public MoveFacadeImpl(MoveService moveService, WarehouseService warehouseService, ResourceService resourceService,
                          MoveMapper moveMapper) {
        this.moveService = moveService;
        this.warehouseService = warehouseService;
        this.resourceService = resourceService;
        this.moveMapper = moveMapper;
    }

    @Override
    public AllMoveResponseBody getAllMove() {
        List<MoveResponseBodyModel> moves = moveService
                .getAllMove().stream()
                .map(moveMapper::toMoveResponseBodyModel)
                .toList();
        AllMoveResponseBody responseBody = new AllMoveResponseBody();
        responseBody.setStatus(StatusResponse.SUCCESS);
        responseBody.setDescription(String.format("Перемещения получены успешно. Количество %d.", moves.size()));
        responseBody.setMoves(moves);
        return responseBody;
    }

    @Override
    @Transactional
    public BaseResponseBody addMove(MoveRequestBody request) {
        Optional<Warehouse> fromWarehouseCandidate = warehouseService.getWarehouseById(request.getFromWarehouseId());
        if (fromWarehouseCandidate.isEmpty()) {
            BaseResponseBody response = new BaseResponseBody();
            response.setStatus(StatusResponse.WAREHOUSE_NOT_FOUND);
            response.setDescription(String.format("Склад с идентификатором = %d не найден.", request.getFromWarehouseId()));
            return response;
        }
        Optional<Warehouse> toWarehouseCandidate = warehouseService.getWarehouseById(request.getToWarehouseId());
        if (toWarehouseCandidate.isEmpty()) {
            BaseResponseBody response = new BaseResponseBody();
            response.setStatus(StatusResponse.WAREHOUSE_NOT_FOUND);
            response.setDescription(String.format("Склад с идентификатором = %d не найден.", request.getToWarehouseId()));
            return response;
        }
        List<Long> requestResourcesIds = request.getResources().stream()
                .map(MoveResourceRequestBody::getResourceId)
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
        Warehouse fromWarehouse = fromWarehouseCandidate.get();
        for (MoveResourceRequestBody resCountReq : request.getResources()) {
            Optional<Remain> remainCandidate = fromWarehouse.getRemainByResourceId(resCountReq.getResourceId());
            if (remainCandidate.isEmpty()) {
                BaseResponseBody response = new BaseResponseBody();
                response.setStatus(StatusResponse.RESOURCE_NOT_FOUND);
                response.setDescription(String.format(
                        "В месте хранения отсутствует ресурс с id: %d.",
                        resCountReq.getResourceId()));
                return response;
            }
            Remain remain = remainCandidate.get();
            if (remain.getCount() < resCountReq.getCount()) {
                BaseResponseBody response = new BaseResponseBody();
                response.setStatus(StatusResponse.NOT_ENOUGH_RESOURCES);
                response.setDescription(String.format(
                        "Не достаточное количество ресурса на складе. Идентификатор ресурса: %d, количество на выдачу: %d, остаток: %d.",
                        resCountReq.getResourceId(), resCountReq.getCount(), remain.getCount()));
                return response;
            }
        }
        List<MoveResource> moveResources = request.getResources().stream()
                .map(r -> MoveResource.create(r.getResourceId(), r.getCount()))
                .toList();

        Warehouse toWarehouse = toWarehouseCandidate.get();
        Move move = Move.create(fromWarehouse, toWarehouse, moveResources);
        move = moveService.save(move);

        fromWarehouse = fromWarehouse.moveRemaining(moveResources);
        warehouseService.save(fromWarehouse);

        toWarehouse = toWarehouse.addRemainingMove(moveResources);
        warehouseService.save(toWarehouse);

        BaseResponseBody response = new BaseResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Перемещение добавлено успешно 'id: %d'.", move.id().orElseThrow()));
        return response;
    }

    @Override
    public MoveResponseBody getMoveById(Long id) {
        Optional<Move> moveCandidate = moveService.findById(id);
        if (moveCandidate.isEmpty()) {
            MoveResponseBody response = new MoveResponseBody();
            response.setStatus(StatusResponse.MOVE_NOT_FOUND);
            response.setDescription(String.format("Перемещение с идентификатором 'id: %d' не найдено", id));
            return response;
        }
        MoveResponseBody response = new MoveResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Перемещение с идентификатором 'id: %d' найдено", id));
        response.setMove(moveMapper.toMoveWithResourcesResponseBodyModel(moveCandidate.get()));
        return response;
    }
}
