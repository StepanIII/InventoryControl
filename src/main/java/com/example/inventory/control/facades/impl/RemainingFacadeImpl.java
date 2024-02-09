package com.example.inventory.control.facades.impl;

import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.facades.RemainingFacade;
import com.example.inventory.control.mapper.RemainMapper;
import com.example.inventory.control.services.RemainingService;
import com.example.inventory.control.api.remain.model.RemainWithWarehouseResponseBodyModel;
import com.example.inventory.control.api.remain.RemainingResponseBody;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class RemainingFacadeImpl implements RemainingFacade {

    private final RemainingService remainingService;
    private final RemainMapper remainMapper;

    public RemainingFacadeImpl(RemainingService remainingService, RemainMapper remainMapper) {
        this.remainingService = remainingService;
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

}
