package com.example.inventory.control.services.impl;

import com.example.inventory.control.entities.ResourceEntity;
import com.example.inventory.control.models.Resource;
import com.example.inventory.control.repositories.ResourceRepository;
import com.example.inventory.control.services.ResourceService;
import com.example.inventory.control.services.mapper.ResourceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;

    public ResourceServiceImpl(ResourceRepository resourceRepository, ResourceMapper resourceMapper) {
        this.resourceRepository = resourceRepository;
        this.resourceMapper = resourceMapper;
    }

    @Override
    @Transactional
    public Resource save(Resource resource) {
        ResourceEntity resourceEntity = resourceRepository.save(resourceMapper.toResourceEntity(resource));
        return resourceMapper.toResource(resourceEntity);
    }

    @Override
    public Optional<Resource> findById(Long id) {
        Optional<ResourceEntity> resourceEntityCandidate = resourceRepository.findById(id);
        if (resourceEntityCandidate.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(resourceMapper.toResource(resourceEntityCandidate.get()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Resource> getListAllResources() {
        return resourceRepository.findAll().stream()
                .map(resourceMapper::toResource)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        resourceRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return resourceRepository.existsById(id);
    }

}
