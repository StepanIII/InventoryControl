package com.example.inventory.control.facades.impl;

import com.example.inventory.control.facades.AcceptanceFacade;
import com.example.inventory.control.models.Acceptance;
import com.example.inventory.control.models.Benefactor;
import com.example.inventory.control.models.Warehouse;
import com.example.inventory.control.services.AcceptanceService;
import com.example.inventory.control.services.BenefactorService;
import com.example.inventory.control.services.WarehouseService;
import com.example.inventory.control.ui.models.requests.acceptance.AddAcceptRequest;
import com.example.inventory.control.ui.models.responses.StatusResponse;
import com.example.inventory.control.ui.models.responses.acceptance.AcceptResponse;
import com.example.inventory.control.ui.models.responses.acceptance.AcceptanceResponse;
import com.example.inventory.control.ui.models.responses.acceptance.AddAcceptResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public final class AcceptanceFacadeImpl implements AcceptanceFacade {

    private final AcceptanceService acceptanceService;
    private final BenefactorService benefactorService;
    private final WarehouseService warehouseService;

    public AcceptanceFacadeImpl(AcceptanceService acceptanceService,
                                BenefactorService benefactorService,
                                WarehouseService warehouseService) {
        this.acceptanceService = acceptanceService;
        this.benefactorService = benefactorService;
        this.warehouseService = warehouseService;
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

    @Override
    public AddAcceptResponse addAccept(AddAcceptRequest request) {
        Optional<Benefactor> benefactorCandidate = benefactorService.getBenefactorById(request.getBenefactorId());
        if (benefactorCandidate.isEmpty()) {
            return new AddAcceptResponse(StatusResponse.ERROR,
                    String.format("Благодетель с идентификатором = %d не найден.", request.getBenefactorId()), null);
        }
        Optional<Warehouse> warehouseCandidate = warehouseService.getWarehouseById(request.getWarehouseId());
        if (warehouseCandidate.isEmpty()) {
            return new AddAcceptResponse(StatusResponse.ERROR,
                    String.format("Место хранения с идентификатором = %d не найдено.", request.getWarehouseId()), null);
        }
        Acceptance createdAcceptance = Acceptance.create(warehouseCandidate.get(), benefactorCandidate.get());
        createdAcceptance = acceptanceService.save(createdAcceptance);
        AcceptResponse acceptResponse = new AcceptResponse(
                createdAcceptance.id().orElseThrow(),
                createdAcceptance.getCreatedTime(),
                createdAcceptance.getWarehouse().getName(),
                createdAcceptance.getBenefactor().getFio());
        return new AddAcceptResponse(StatusResponse.SUCCESS,
                String.format("Примка добавлена успешно 'id: %d'.", createdAcceptance.id().orElseThrow()),
                acceptResponse);
    }
}
