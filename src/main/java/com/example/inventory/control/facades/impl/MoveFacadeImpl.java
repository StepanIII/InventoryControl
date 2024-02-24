package com.example.inventory.control.facades.impl;

import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.move.AllMoveResponseBody;
import com.example.inventory.control.api.resource.operation.move.model.MoveResponseBodyModel;
import com.example.inventory.control.facades.MoveFacade;
import com.example.inventory.control.mapper.MoveMapper;
import com.example.inventory.control.services.MoveService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoveFacadeImpl implements MoveFacade {

    private final MoveService moveService;
    private final MoveMapper moveMapper;

    public MoveFacadeImpl(MoveService moveService, MoveMapper moveMapper) {
        this.moveService = moveService;
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
}
