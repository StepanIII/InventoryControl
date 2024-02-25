package com.example.inventory.control.services.impl;

import com.example.inventory.control.domain.models.Move;
import com.example.inventory.control.entities.MoveEntity;
import com.example.inventory.control.mapper.MoveMapper;
import com.example.inventory.control.repositories.MoveRepository;
import com.example.inventory.control.services.MoveService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Override
    @Transactional
    public Move save(Move move) {
        MoveEntity moveEntity = moveMapper.toEntity(move);
        moveEntity = moveRepository.save(moveEntity);
        return moveMapper.toDomainModel(moveEntity);
    }

    @Override
    @Transactional
    public Optional<Move> findById(Long id) {
        Optional<MoveEntity> moveEntityCandidate = moveRepository.findById(id);
        return moveEntityCandidate.map(moveMapper::toDomainModel);
    }
}
