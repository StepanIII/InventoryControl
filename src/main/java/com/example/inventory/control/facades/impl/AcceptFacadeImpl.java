package com.example.inventory.control.facades.impl;

import com.example.inventory.control.api.acceptance.AcceptRequest;
import com.example.inventory.control.api.acceptance.AcceptResourcesResponse;
import com.example.inventory.control.api.acceptance.AcceptanceResponse;
import com.example.inventory.control.api.acceptance.ResourceCountRequest;
import com.example.inventory.control.api.BaseResponse;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.acceptance.model.AcceptDto;
import com.example.inventory.control.domain.models.Accept;
import com.example.inventory.control.domain.models.AcceptResourceCount;
import com.example.inventory.control.domain.models.Benefactor;
import com.example.inventory.control.domain.models.Warehouse;
import com.example.inventory.control.facades.AcceptFacade;
import com.example.inventory.control.mapper.AcceptMapper;
import com.example.inventory.control.services.AcceptService;
import com.example.inventory.control.services.BenefactorService;
import com.example.inventory.control.services.ResourceService;
import com.example.inventory.control.services.WarehouseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class AcceptFacadeImpl implements AcceptFacade {

    private final AcceptService acceptService;
    private final BenefactorService benefactorService;
    private final ResourceService resourceService;
    private final WarehouseService warehouseService;
    private final AcceptMapper acceptMapper;

    public AcceptFacadeImpl(AcceptService acceptService, BenefactorService benefactorService,
                            ResourceService resourceService, WarehouseService warehouseService,
                            AcceptMapper acceptMapper) {
        this.acceptService = acceptService;
        this.benefactorService = benefactorService;
        this.resourceService = resourceService;
        this.warehouseService = warehouseService;
        this.acceptMapper = acceptMapper;
    }

    @Override
    public AcceptanceResponse getAllAcceptance() {
        List<AcceptDto> acceptance = acceptService.getListAllAcceptance().stream()
                .map(acceptMapper::toDto)
                .toList();
        AcceptanceResponse response = new AcceptanceResponse();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Приемки получены успешно. Количество %d.", acceptance.size()));
        response.setAcceptance(acceptance);
        return response;
    }

    @Override
    public AcceptResourcesResponse getAcceptById(Long id) {
        Optional<Accept> acceptanceCandidate = acceptService.findById(id);
        if (acceptanceCandidate.isEmpty()) {
            AcceptResourcesResponse response = new AcceptResourcesResponse();
            response.setStatus(StatusResponse.ACCEPT_NOT_FOUND);
            response.setDescription(String.format("Приемка с идентификатором 'id: %d' не найдена", id));
            return response;
        }
        AcceptResourcesResponse response = new AcceptResourcesResponse();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Приемка с идентификатором 'id: %d' найдена", id));
        response.setAccept(acceptMapper.toResponse(acceptanceCandidate.get()));
        return response;
    }

    @Override
    @Transactional
    public BaseResponse addAccept(AcceptRequest request) {
        Optional<Benefactor> benefactorCandidate = benefactorService.getBenefactorById(request.getBenefactorId());
        if (benefactorCandidate.isEmpty()) {
            BaseResponse response = new BaseResponse();
            response.setStatus(StatusResponse.BENEFACTOR_NOT_FOUND);
            response.setDescription(String.format("Благодетель с id: %d не найден.", request.getBenefactorId()));
            return response;
        }
        Optional<Warehouse> warehouseCandidate = warehouseService.getWarehouseById(request.getWarehouseId());
        if (warehouseCandidate.isEmpty()) {
            BaseResponse response = new BaseResponse();
            response.setStatus(StatusResponse.WAREHOUSE_NOT_FOUND);
            response.setDescription(String.format("Место хранения с идентификатором = %d не найдено.", request.getWarehouseId()));
            return response;
        }
        List<Long> requestResourcesIds = request.getResources().stream()
                .map(ResourceCountRequest::getResourceId)
                .toList();
        boolean isExistsAllResources = resourceService.existsAllByIds(requestResourcesIds);
        if (!isExistsAllResources) {
            BaseResponse response = new BaseResponse();
            response.setStatus(StatusResponse.RESOURCE_NOT_FOUND);
            response.setDescription(String.format(
                    "Не найдены все ресурсы по списку идентификаторов: %s.",
                    StringUtils.collectionToDelimitedString(requestResourcesIds, ";")));
            return response;
        }
        List<AcceptResourceCount> acceptResourceCounts = request.getResources().stream()
                .map(r -> AcceptResourceCount.create(r.getResourceId(), r.getCount()))
                .toList();
        Accept createdAcceptance = Accept.create(warehouseCandidate.get(), benefactorCandidate.get(), acceptResourceCounts);
        createdAcceptance = acceptService.save(createdAcceptance);
        Warehouse warehouse = warehouseCandidate.get();
        warehouse = warehouse.addResources(acceptResourceCounts);
        warehouseService.save(warehouse);
        BaseResponse response = new BaseResponse();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Примка добавлена успешно 'id: %d'.", createdAcceptance.id().orElseThrow()));
        return response;
    }

//    @Override
//    public BaseResponse updateAccept(Long id, AcceptRequest request) {
//        Optional<Accept> acceptanceCandidate = acceptanceService.findById(id);
//        if (acceptanceCandidate.isEmpty()) {
//            BaseResponse response = new BaseResponse();
//            response.setStatus(StatusResponse.ACCEPT_NOT_FOUND);
//            response.setDescription(String.format("Приемка с идентификатором 'id: %d' не найдена", id));
//            return response;
//        }
//        Optional<Benefactor> benefactorCandidate = benefactorService.getBenefactorById(request.getBenefactorId());
//        if (benefactorCandidate.isEmpty()) {
//            BaseResponse response = new BaseResponse();
//            response.setStatus(StatusResponse.BENEFACTOR_NOT_FOUND);
//            response.setDescription(String.format("Благодетель с id: %d не найден.", request.getBenefactorId()));
//            return response;
//        }
//        Optional<Warehouse> warehouseCandidate = warehouseService.getWarehouseById(request.getWarehouseId());
//        if (warehouseCandidate.isEmpty()) {
//            BaseResponse response = new BaseResponse();
//            response.setStatus(StatusResponse.WAREHOUSE_NOT_FOUND);
//            response.setDescription(String.format("Место хранения с идентификатором = %d не найдено.", request.getWarehouseId()));
//            return response;
//        }
//        List<Long> requestResourcesIds = request.getResources().stream()
//                .map(ResourceCountRequest::getResourceId)
//                .toList();
//        boolean isExistsAllResources = resourceService.existsAllByIds(requestResourcesIds);
//        if (!isExistsAllResources) {
//            BaseResponse response = new BaseResponse();
//            response.setStatus(StatusResponse.RESOURCE_NOT_FOUND);
//            response.setDescription(String.format(
//                    "Не найдены все ресурсы по списку идентификаторов: %s.",
//                    StringUtils.collectionToDelimitedString(requestResourcesIds, ";")));
//            return response;
//        }
//        List<AcceptResourceCount> acceptResourceCounts = request.getResources().stream()
//                .map(r -> AcceptResourceCount.create(r.getResourceId(), r.getCount()))
//                .toList();
//        Accept acceptance = acceptanceCandidate.get();
//        Accept updatedAcceptance = acceptance
//                .updateBenefactor(benefactorCandidate.get())
//                .updateWarehouse(warehouseCandidate.get())
//                .updateResources(acceptResourceCounts);
//        updatedAcceptance = acceptanceService.save(updatedAcceptance);
//
//
//
//        Warehouse warehouse = warehouseCandidate.get();
//        warehouse.
//
//        BaseResponse response = new BaseResponse();
//        response.setStatus(StatusResponse.SUCCESS);
//        response.setDescription(String.format("Приемка обновлена успешно 'id: %d'.", updatedAcceptance.id().orElseThrow()));
//        return response;
//    }


}