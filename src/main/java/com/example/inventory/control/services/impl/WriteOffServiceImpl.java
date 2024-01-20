package com.example.inventory.control.services.impl;

import com.example.inventory.control.models.WriteOff;
import com.example.inventory.control.repositories.WriteOffRepository;
import com.example.inventory.control.services.WriteOffService;
import com.example.inventory.control.services.mapper.WriteOffMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WriteOffServiceImpl implements WriteOffService {

    private final WriteOffRepository writeOffRepository;
    private final WriteOffMapper writeOffMapper;

    public WriteOffServiceImpl(WriteOffRepository writeOffRepository, WriteOffMapper writeOffMapper) {
        this.writeOffRepository = writeOffRepository;
        this.writeOffMapper = writeOffMapper;
    }

    @Override
    public List<WriteOff> getListAllWriteOff() {
        return writeOffRepository.findAll().stream().map(writeOffMapper::toDomain).toList();
    }

}
