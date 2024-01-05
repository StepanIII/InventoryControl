package com.example.inventory.control.facades.impl;

import com.example.inventory.control.facades.AcceptanceFacade;
import com.example.inventory.control.services.AcceptanceService;
import com.example.inventory.control.ui.models.responses.acceptance.AcceptResponse;
import com.example.inventory.control.ui.models.responses.acceptance.AcceptanceResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public final class AcceptanceFacadeImpl implements AcceptanceFacade {

    private final AcceptanceService acceptanceService;

    public AcceptanceFacadeImpl(AcceptanceService acceptanceService) {
        this.acceptanceService = acceptanceService;
    }

    @Override
    public AcceptanceResponse getListAllAcceptance() {
        List<AcceptResponse> listAcceptResponse = acceptanceService
                .getListAllAcceptance().stream()
                .map(a -> new AcceptResponse(
                        a.id().orElseThrow(),
                        a.getCreatedTime(),
                        a.getWarehouse().getName(),
                        a.getBenefactor().getFio()))
                .collect(Collectors.toList());
        return new AcceptanceResponse(listAcceptResponse);
    }
}
