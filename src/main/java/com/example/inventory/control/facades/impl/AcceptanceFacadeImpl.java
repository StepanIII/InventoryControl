package com.example.inventory.control.facades.impl;

import com.example.inventory.control.facades.AcceptanceFacade;
import com.example.inventory.control.models.Acceptance;
import com.example.inventory.control.models.Benefactor;
import com.example.inventory.control.models.AcceptResourceCount;
import com.example.inventory.control.models.Warehouse;
import com.example.inventory.control.services.AcceptanceService;
import com.example.inventory.control.services.BenefactorService;
import com.example.inventory.control.services.ResourceService;
import com.example.inventory.control.services.WarehouseService;
import com.example.inventory.control.ui.models.requests.acceptance.AddAcceptRequest;
import com.example.inventory.control.ui.models.requests.acceptance.ResourceCountRequest;
import com.example.inventory.control.ui.models.requests.acceptance.UpdateAcceptRequest;
import com.example.inventory.control.ui.models.responses.StatusResponse;
import com.example.inventory.control.ui.models.responses.acceptance.AcceptResourcesResponse;
import com.example.inventory.control.ui.models.responses.acceptance.AcceptResponse;
import com.example.inventory.control.ui.models.responses.acceptance.AcceptanceResponse;
import com.example.inventory.control.ui.models.responses.acceptance.AddAcceptResponse;
import com.example.inventory.control.ui.models.responses.acceptance.ResourceCountResponse;
import com.example.inventory.control.ui.models.responses.acceptance.UpdateAcceptResponse;
import com.example.inventory.control.ui.models.responses.benefactor.BenefactorResponse;
import com.example.inventory.control.ui.models.responses.warehouse.WarehouseResponse;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public final class AcceptanceFacadeImpl implements AcceptanceFacade {

    private final AcceptanceService acceptanceService;
    private final BenefactorService benefactorService;
    private final ResourceService resourceService;
    private final WarehouseService warehouseService;

    public AcceptanceFacadeImpl(AcceptanceService acceptanceService,
                                BenefactorService benefactorService,
                                ResourceService resourceService,
                                WarehouseService warehouseService) {
        this.acceptanceService = acceptanceService;
        this.benefactorService = benefactorService;
        this.resourceService = resourceService;
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
        List<Long> expectedResourceIds = request.getResources().stream().map(ResourceCountRequest::getResourceId).toList();
        List<Long> verifiableResourceIds = resourceService.findAllIds(expectedResourceIds);
        List<Long> absentResourceIds = checkAvailabilityResources(verifiableResourceIds, expectedResourceIds);
        if (!absentResourceIds.isEmpty()) {
            List<String> stringIds = absentResourceIds.stream().map(String::valueOf).toList();
            return new AddAcceptResponse(StatusResponse.ERROR,
                    String.format("Ресурсы не найдены 'ids: %s'.", String.join(",", stringIds)), null);
        }
        List<AcceptResourceCount> acceptResourceCounts = request.getResources().stream()
                .map(r -> AcceptResourceCount.create(r.getResourceId(), r.getCount()))
                .toList();
        Acceptance createdAcceptance = Acceptance.create(warehouseCandidate.get(), benefactorCandidate.get(), acceptResourceCounts);
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

    @Override
    public AcceptResourcesResponse getAcceptById(Long id) {
        Optional<Acceptance> acceptanceCandidate = acceptanceService.findById(id);
        if (acceptanceCandidate.isEmpty()) {
            return new AcceptResourcesResponse(
                    StatusResponse.ERROR,
                    String.format("Приемка с идентификатором 'id: %d' не найдена", id));
        }
        Acceptance acceptance = acceptanceCandidate.get();
        Benefactor benefactor = acceptance.getBenefactor();
        Warehouse warehouse = acceptance.getWarehouse();

        BenefactorResponse benefactorResponse = new BenefactorResponse(benefactor.id().orElseThrow(), benefactor.getFio());
        WarehouseResponse warehouseResponse = new WarehouseResponse(warehouse.id().orElseThrow(), warehouse.getName());
        List<ResourceCountResponse> resourcesResponse = acceptance.getResources().stream()
                .map(r -> new ResourceCountResponse(
                        r.id().orElseThrow(),
                        r.getResourceId(),
                        r.getName(),
                        r.getCount()))
                .toList();

        return new AcceptResourcesResponse(
                StatusResponse.SUCCESS,
                String.format("Приемка с идентификатором 'id: %d' найдена", id),
                acceptance.id().orElseThrow(),
                acceptance.getCreatedTime(),
                warehouseResponse,
                benefactorResponse,
                resourcesResponse);
    }

    @Override
    public UpdateAcceptResponse updateAccept(Long id, UpdateAcceptRequest request) {
        Optional<Acceptance> acceptanceCandidate = acceptanceService.findById(id);
        if (acceptanceCandidate.isEmpty()) {
            return new UpdateAcceptResponse(
                    StatusResponse.ERROR,
                    String.format("Приемка с идентификатором 'id: %d' не найдена", id));
        }
        Optional<Benefactor> benefactorCandidate = benefactorService.getBenefactorById(request.getBenefactorId());
        if (benefactorCandidate.isEmpty()) {
            return new UpdateAcceptResponse(StatusResponse.ERROR,
                    String.format("Благодетель с идентификатором = %d не найден.", request.getBenefactorId()));
        }
        Optional<Warehouse> warehouseCandidate = warehouseService.getWarehouseById(request.getWarehouseId());
        if (warehouseCandidate.isEmpty()) {
            return new UpdateAcceptResponse(StatusResponse.ERROR,
                    String.format("Место хранения с идентификатором = %d не найдено.", request.getWarehouseId()));
        }
        List<Long> expectedResourceIds = request.getResources().stream().map(ResourceCountRequest::getResourceId).toList();
        List<Long> verifiableResourceIds = resourceService.findAllIds(expectedResourceIds);
        List<Long> absentResourceIds = checkAvailabilityResources(verifiableResourceIds, expectedResourceIds);
        if (!absentResourceIds.isEmpty()) {
            List<String> stringIds = absentResourceIds.stream().map(String::valueOf).toList();
            return new UpdateAcceptResponse(StatusResponse.ERROR,
                    String.format("Ресурсы не найдены 'ids: %s'.", String.join(",", stringIds)));
        }
        List<AcceptResourceCount> acceptResourceCounts = request.getResources().stream()
                .map(r -> AcceptResourceCount.create(r.getResourceId(), r.getCount()))
                .toList();
        Acceptance acceptance = acceptanceCandidate.get();
        Acceptance updatedAcceptance = acceptance
                .updateBenefactor(benefactorCandidate.get())
                .updateWarehouse(warehouseCandidate.get())
                .updateResources(acceptResourceCounts);
        updatedAcceptance = acceptanceService.save(updatedAcceptance);
        return new UpdateAcceptResponse(StatusResponse.SUCCESS,
                String.format("Приемка обновлена успешно 'id: %d'.", updatedAcceptance.id().orElseThrow()));
    }

    private List<Long> checkAvailabilityResources(List<Long> verifiableIds, List<Long> expectedIds) {
        List<Long> absentIds = new LinkedList<>();
        for (Long expectedId : expectedIds) {
            if (!verifiableIds.contains(expectedId)) {
                absentIds.add(expectedId);
            }
        }
        return absentIds;
    }
}
