package com.example.inventory.control.services.impl;

import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.domain.models.Resource;
import com.example.inventory.control.repositories.ResourceRepository;
import com.example.inventory.control.services.ResourceService;
import com.example.inventory.control.mapper.ResourceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;

    public ResourceServiceImpl(ResourceRepository resourceRepository, ResourceMapper resourceMapper) {
        this.resourceRepository = resourceRepository;
        this.resourceMapper = resourceMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Resource> getListAllResources() {
        return resourceRepository.findAll().stream()
                .map(resourceMapper::toDomainModel)
                .toList();
    }

    @Override
    @Transactional
    public Resource save(Resource resource) {
        ResourceEntity resourceEntity = resourceMapper.toEntity(resource);
        resourceEntity = resourceRepository.save(resourceEntity);
        return resourceMapper.toDomainModel(resourceEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Resource> findById(Long id) {
        Optional<ResourceEntity> resourceEntityCandidate = resourceRepository.findById(id);
        return resourceEntityCandidate.map(resourceMapper::toDomainModel);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        resourceRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return resourceRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsAllByIds(List<Long> ids) {
        List<Long> foundIds = resourceRepository.findIdsByIds(ids);
        return foundIds.size() == ids.size();
    }

}
