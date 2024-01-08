package com.example.inventory.control.services.impl;

import com.example.inventory.control.entities.WarehouseEntity;
import com.example.inventory.control.models.Warehouse;
import com.example.inventory.control.repositories.WarehouseRepository;
import com.example.inventory.control.services.WarehouseService;
import com.example.inventory.control.services.mapper.WarehouseMapper;
import org.springframework.stereotype.Service;

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
    public List<Warehouse> getAllListWarehouses() {
        return warehouseRepository.findAll().stream()
                .map(warehouseMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Warehouse> getWarehouseById(Long id) {
        Optional<WarehouseEntity> warehouseEntityCandidate = warehouseRepository.findById(id);
        return warehouseEntityCandidate.map(warehouseMapper::toDomain);
    }

}
