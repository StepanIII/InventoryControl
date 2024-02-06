package com.example.inventory.control.facades.impl;

import com.example.inventory.control.facades.RemainingFacade;
import com.example.inventory.control.services.RemainingService;
import com.example.inventory.control.api.responses.remaining.RemainResponse;
import com.example.inventory.control.api.responses.remaining.RemainingResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class RemainingFacadeImpl implements RemainingFacade {

    private final RemainingService remainingService;

    public RemainingFacadeImpl(RemainingService remainingService) {
        this.remainingService = remainingService;
    }

    @Override
    public RemainingResponse getListAllRemaining() {
        List<RemainResponse> remainResponseList = remainingService
                .getListRemaining().stream()
                .map(r -> new RemainResponse(r.getResourceId(), r.getResourceName(), r.getCount(), r.getWarehouseName()))
                .toList();
        return new RemainingResponse(remainResponseList);
    }

}
