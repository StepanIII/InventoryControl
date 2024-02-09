package com.example.inventory.control.facades.impl;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.StatusResponse;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptRequestBody;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptResourceCountRequestBody;
import com.example.inventory.control.api.resource.operation.acceptance.AcceptResponseBody;
import com.example.inventory.control.api.resource.operation.acceptance.AllAcceptResponseBody;
import com.example.inventory.control.api.resource.operation.acceptance.model.AcceptResponseBodyModel;
import com.example.inventory.control.api.resource.operation.issue.AllIssueResponseBody;
import com.example.inventory.control.api.resource.operation.issue.IssueRequestBody;
import com.example.inventory.control.api.resource.operation.issue.IssueResourceCountRequestBody;
import com.example.inventory.control.api.resource.operation.issue.IssueResponseBody;
import com.example.inventory.control.api.resource.operation.issue.model.IssueResponseBodyModel;
import com.example.inventory.control.domain.models.Client;
import com.example.inventory.control.domain.models.Remain;
import com.example.inventory.control.domain.models.ResourceCount;
import com.example.inventory.control.domain.models.ResourceOperation;
import com.example.inventory.control.domain.models.Warehouse;
import com.example.inventory.control.enums.ClientType;
import com.example.inventory.control.enums.ResourceOperationType;
import com.example.inventory.control.facades.ResourceOperationFacade;
import com.example.inventory.control.mapper.ResourceOperationMapper;
import com.example.inventory.control.services.ClientService;
import com.example.inventory.control.services.ResourceOperationService;
import com.example.inventory.control.services.ResourceService;
import com.example.inventory.control.services.WarehouseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceOperationFacadeImpl implements ResourceOperationFacade {

    private final ResourceOperationService resourceOperationService;
    private final ClientService clientService;
    private final ResourceService resourceService;
    private final WarehouseService warehouseService;
    private final ResourceOperationMapper resourceOperationMapper;

    public ResourceOperationFacadeImpl(ResourceOperationService resourceOperationService, ClientService clientService,
                                       ResourceService resourceService, WarehouseService warehouseService,
                                       ResourceOperationMapper resourceOperationMapper) {
        this.resourceOperationService = resourceOperationService;
        this.clientService = clientService;
        this.resourceService = resourceService;
        this.warehouseService = warehouseService;
        this.resourceOperationMapper = resourceOperationMapper;
    }

    @Override
    public AllAcceptResponseBody getAllAccept() {
        List<AcceptResponseBodyModel> acceptance = resourceOperationService
                .getAllResourceOperationByType(ResourceOperationType.ACCEPT).stream()
                .map(resourceOperationMapper::toAcceptResponseBodyModel)
                .toList();
        AllAcceptResponseBody responseBody = new AllAcceptResponseBody();
        responseBody.setStatus(StatusResponse.SUCCESS);
        responseBody.setDescription(String.format("Приемки получены успешно. Количество %d.", acceptance.size()));
        responseBody.setAcceptance(acceptance);
        return responseBody;
    }

    @Override
    public AcceptResponseBody getAcceptById(Long id) {
        Optional<ResourceOperation> acceptanceCandidate =
                resourceOperationService.findResourceOperationByIdAndType(id, ResourceOperationType.ACCEPT);
        if (acceptanceCandidate.isEmpty()) {
            AcceptResponseBody response = new AcceptResponseBody();
            response.setStatus(StatusResponse.ACCEPT_NOT_FOUND);
            response.setDescription(String.format("Приемка с идентификатором 'id: %d' не найдена", id));
            return response;
        }
        AcceptResponseBody response = new AcceptResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Приемка с идентификатором 'id: %d' найдена", id));
        response.setAccept(resourceOperationMapper.acceptWithResourcesBodyModel(acceptanceCandidate.get()));
        return response;
    }

    @Override
    @Transactional
    public BaseResponseBody addAccept(AcceptRequestBody request) {
        Optional<Client> benefactorCandidate =
                clientService.getClientByIdAndType(request.getBenefactorId(), ClientType.BENEFACTOR);
        if (benefactorCandidate.isEmpty()) {
            BaseResponseBody response = new BaseResponseBody();
            response.setStatus(StatusResponse.BENEFACTOR_NOT_FOUND);
            response.setDescription(String.format("Благодетель с id: %d не найден.", request.getBenefactorId()));
            return response;
        }
        Optional<Warehouse> warehouseCandidate = warehouseService.getWarehouseById(request.getWarehouseId());
        if (warehouseCandidate.isEmpty()) {
            BaseResponseBody response = new BaseResponseBody();
            response.setStatus(StatusResponse.WAREHOUSE_NOT_FOUND);
            response.setDescription(String.format("Место хранения с идентификатором = %d не найдено.", request.getWarehouseId()));
            return response;
        }
        List<Long> requestResourcesIds = request.getResources().stream()
                .map(AcceptResourceCountRequestBody::getResourceId)
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
        List<ResourceCount> acceptResourceCounts = request.getResources().stream()
                .map(r -> ResourceCount.create(r.getResourceId(), r.getCount()))
                .toList();
        ResourceOperation createdAcceptance = ResourceOperation.create(
                ResourceOperationType.ACCEPT,
                warehouseCandidate.get(),
                benefactorCandidate.get(),
                acceptResourceCounts);
        createdAcceptance = resourceOperationService.saveOperation(createdAcceptance);
        Warehouse warehouse = warehouseCandidate.get();
        warehouse = warehouse.addRemaining(acceptResourceCounts);
        warehouseService.save(warehouse);
        BaseResponseBody response = new BaseResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Приемка добавлена успешно 'id: %d'.", createdAcceptance.id().orElseThrow()));
        return response;
    }

    @Override
    public AllIssueResponseBody getAllIssue() {
        List<IssueResponseBodyModel> issuance = resourceOperationService
                .getAllResourceOperationByType(ResourceOperationType.ISSUE).stream()
                .map(resourceOperationMapper::toIssueResponseBodyModel)
                .toList();
        AllIssueResponseBody responseBody = new AllIssueResponseBody();
        responseBody.setStatus(StatusResponse.SUCCESS);
        responseBody.setDescription(String.format("Выдачи получены успешно. Количество %d.", issuance.size()));
        responseBody.setIssuance(issuance);
        return responseBody;
    }

    @Override
    public IssueResponseBody getIssueById(Long id) {
        Optional<ResourceOperation> issueCandidate =
                resourceOperationService.findResourceOperationByIdAndType(id, ResourceOperationType.ISSUE);
        if (issueCandidate.isEmpty()) {
            IssueResponseBody response = new IssueResponseBody();
            response.setStatus(StatusResponse.ISSUE_NOT_FOUND);
            response.setDescription(String.format("Выдача с идентификатором 'id: %d' не найдена", id));
            return response;
        }
        IssueResponseBody response = new IssueResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Приемка с идентификатором 'id: %d' найдена", id));
        response.setIssue(resourceOperationMapper.toIssueWithResourcesBodyModel(issueCandidate.get()));
        return response;
    }

    @Override
    @Transactional
    public BaseResponseBody addIssue(IssueRequestBody request) {
        Optional<Client> beneficiaryCandidate =
                clientService.getClientByIdAndType(request.getBeneficiaryId(), ClientType.BENEFICIARY);
        if (beneficiaryCandidate.isEmpty()) {
            BaseResponseBody response = new BaseResponseBody();
            response.setStatus(StatusResponse.BENEFICIARY_NOT_FOUND);
            response.setDescription(String.format("Благополучатель с id: %d не найден.", request.getBeneficiaryId()));
            return response;
        }
        Optional<Warehouse> warehouseCandidate = warehouseService.getWarehouseById(request.getWarehouseId());
        if (warehouseCandidate.isEmpty()) {
            BaseResponseBody response = new BaseResponseBody();
            response.setStatus(StatusResponse.WAREHOUSE_NOT_FOUND);
            response.setDescription(String.format("Место хранения с идентификатором = %d не найдено.", request.getWarehouseId()));
            return response;
        }
        List<Long> requestResourcesIds = request.getResources().stream()
                .map(IssueResourceCountRequestBody::getResourceId)
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
        Warehouse warehouse = warehouseCandidate.get();
        for (IssueResourceCountRequestBody resCountReq : request.getResources()) {
            Optional<Remain> remainCandidate = warehouse.getRemainByResourceId(resCountReq.getResourceId());
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
        List<ResourceCount> issueResourceCounts = request.getResources().stream()
                .map(r -> ResourceCount.create(r.getResourceId(), r.getCount()))
                .toList();
        ResourceOperation createdIssue = ResourceOperation.create(
                ResourceOperationType.ISSUE,
                warehouseCandidate.get(),
                beneficiaryCandidate.get(),
                issueResourceCounts);
        createdIssue = resourceOperationService.saveOperation(createdIssue);
        warehouse = warehouse.writeOffRemaining(issueResourceCounts);
        warehouseService.save(warehouse);
        BaseResponseBody response = new BaseResponseBody();
        response.setStatus(StatusResponse.SUCCESS);
        response.setDescription(String.format("Выдача добавлена успешно 'id: %d'.", createdIssue.id().orElseThrow()));
        return response;
    }

}
