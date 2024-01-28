package com.example.inventory.control.services.impl;

import com.example.inventory.control.entities.WriteOffEntity;
import com.example.inventory.control.models.WriteOff;
import com.example.inventory.control.repositories.WriteOffRepository;
import com.example.inventory.control.services.WriteOffService;
import com.example.inventory.control.services.mapper.WriteOffCountMapper;
import com.example.inventory.control.services.mapper.WriteOffMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WriteOffServiceImpl implements WriteOffService {

    private final WriteOffRepository writeOffRepository;
    private final WriteOffMapper writeOffMapper;
    private final WriteOffCountMapper writeOffCountMapper;

    public WriteOffServiceImpl(WriteOffRepository writeOffRepository, WriteOffMapper writeOffMapper,
                               WriteOffCountMapper writeOffCountMapper) {
        this.writeOffRepository = writeOffRepository;
        this.writeOffMapper = writeOffMapper;
        this.writeOffCountMapper = writeOffCountMapper;
    }

    @Override
    public List<WriteOff> getListAllWriteOff() {
        return writeOffRepository.findAll().stream().map(writeOffMapper::toDomain).toList();
    }

    @Override
    public Optional<WriteOff> find(Long id) {
        return writeOffRepository.findById(id).map(writeOffMapper::toDomain);
    }

    @Override
    @Transactional
    public WriteOff save(WriteOff writeOff) {
        WriteOffEntity entity = writeOffMapper.toEntity(writeOff);
        return writeOffMapper.toDomain(writeOffRepository.save(entity));
    }

    @Override
    @Transactional
    public WriteOff update(WriteOff writeOff) {
        WriteOffEntity entity = writeOffRepository.findById(writeOff.id().orElseThrow()).orElseThrow();
        entity.getResourceCounts().clear();
        entity.getResourceCounts().addAll(writeOff.getWriteOffResourceCounts().stream().map(writeOffCountMapper::toEntity).collect(Collectors.toSet()));
        return writeOffMapper.toDomain(writeOffRepository.save(entity));
    }

}
