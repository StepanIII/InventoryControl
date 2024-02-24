package com.example.inventory.control.services.impl;

import com.example.inventory.control.domain.models.Move;
import com.example.inventory.control.entities.MoveEntity;
import com.example.inventory.control.mapper.MoveMapper;
import com.example.inventory.control.repositories.MoveRepository;
import com.example.inventory.control.services.MoveService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MoveServiceImpl implements MoveService {

    private final MoveRepository moveRepository;
    private final MoveMapper moveMapper;

    public MoveServiceImpl(MoveRepository moveRepository, MoveMapper moveMapper) {
        this.moveRepository = moveRepository;
        this.moveMapper = moveMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Move> getAllMove() {
        List<MoveEntity> moveEntities = moveRepository.findAll();
        return moveMapper.toDomainModel(moveEntities);
    }
}
