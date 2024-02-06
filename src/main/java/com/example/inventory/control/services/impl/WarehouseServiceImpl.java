package com.example.inventory.control.services.impl;

import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.domain.models.Warehouse;
import com.example.inventory.control.repositories.WarehouseRepository;
import com.example.inventory.control.services.WarehouseService;
import com.example.inventory.control.mapper.RemainingMapper;
import com.example.inventory.control.mapper.WarehouseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository, WarehouseMapper warehouseMapper) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseMapper = warehouseMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Warehouse> getAllListWarehouses() {
        return warehouseRepository.findAll().stream()
                .map(warehouseMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Warehouse> getWarehouseById(Long id) {
        Optional<WarehouseEntity> warehouseEntityCandidate = warehouseRepository.findById(id);
        return warehouseEntityCandidate.map(warehouseMapper::toDomain);
    }

    @Override
    @Transactional
    public Warehouse save(Warehouse warehouse) {
        WarehouseEntity entity = warehouseMapper.toEntity(warehouse);
        entity = warehouseRepository.save(entity);
        return warehouseMapper.toDomain(entity);
    }

}
