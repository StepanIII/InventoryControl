package com.example.inventory.control.facades.impl;

import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.facades.RemainingFacade;
import com.example.inventory.control.mapper.RemainingMapper;
import com.example.inventory.control.services.RemainingService;
import com.example.inventory.control.api.remaining.model.RemainBodyResponse;
import com.example.inventory.control.api.remaining.RemainingResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class RemainingFacadeImpl implements RemainingFacade {

    private final RemainingService remainingService;
    private final RemainingMapper remainingMapper;

    public RemainingFacadeImpl(RemainingService remainingService, RemainingMapper remainingMapper) {
        this.remainingService = remainingService;
        this.remainingMapper = remainingMapper;
    }

    @Override
    public RemainingResponse getListAllRemaining() {
        List<RemainBodyResponse> remainingResponseList = remainingService.getListRemaining().stream()
                .map(remainingMapper::toBodyResponse)
                .toList();
        RemainingResponse response = new RemainingResponse();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Остатки получены успешно. Количество: %d.", remainingResponseList.size()));
        response.setRemaining(remainingResponseList);
        return response;
    }

}
