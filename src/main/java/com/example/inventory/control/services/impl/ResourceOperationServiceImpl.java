package com.example.inventory.control.services.impl;

import com.example.inventory.control.domain.models.ResourceOperation;
import com.example.inventory.control.entities.ResourceOperationEntity;
import com.example.inventory.control.enums.ResourceOperationType;
import com.example.inventory.control.mapper.ResourceOperationMapper;
import com.example.inventory.control.repositories.ResourceOperationRepository;
import com.example.inventory.control.services.ResourceOperationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResourceOperationServiceImpl implements ResourceOperationService {

    private final ResourceOperationRepository resourceOperationRepository;
    private final ResourceOperationMapper resourceOperationMapper;

    public ResourceOperationServiceImpl(ResourceOperationRepository resourceOperationRepository,
                                        ResourceOperationMapper resourceOperationMapper) {
        this.resourceOperationRepository = resourceOperationRepository;
        this.resourceOperationMapper = resourceOperationMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceOperation> getAllResourceOperationByType(ResourceOperationType type) {
        List<ResourceOperationEntity> operationEntities = resourceOperationRepository.findAllByType(type);
        return operationEntities.stream()
                .map(resourceOperationMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResourceOperation> findResourceOperationByIdAndType(Long id, ResourceOperationType type) {
        Optional<ResourceOperationEntity> acceptanceEntityCandidate = resourceOperationRepository.findByIdAndType(id, type);
        return acceptanceEntityCandidate.map(resourceOperationMapper::toDomain);
    }

    @Override
    @Transactional
    public ResourceOperation saveOperation(ResourceOperation resourceOperation) {
        ResourceOperationEntity resourceOperationEntity = resourceOperationMapper.toEntity(resourceOperation);
        resourceOperationEntity = resourceOperationRepository.save(resourceOperationEntity);
        return resourceOperationMapper.toDomain(resourceOperationEntity);
    }

}
