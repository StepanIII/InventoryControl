package com.example.inventory.control.services.impl;

import com.example.inventory.control.entities.ClientEntity;
import com.example.inventory.control.domain.models.Client;
import com.example.inventory.control.enums.ClientType;
import com.example.inventory.control.mapper.ClientMapper;
import com.example.inventory.control.repositories.ClientRepository;
import com.example.inventory.control.services.ClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> getAllClient(ClientType type) {
        return clientRepository.findAllByType(type).stream()
                .map(clientMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> getClientByIdAndType(Long id, ClientType type) {
        Optional<ClientEntity> benefactorEntityCandidate = clientRepository.findByIdAndType(id, type);
        return benefactorEntityCandidate.map(clientMapper::toDomain);
    }

}