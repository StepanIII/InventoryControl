package com.example.inventory.control.services.impl;

import com.example.inventory.control.domain.models.Role;
import com.example.inventory.control.entities.RoleEntity;
import com.example.inventory.control.repositories.RoleRepository;
import com.example.inventory.control.services.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findByName(String name) {
        Optional<RoleEntity> roleEntityCandidate = roleRepository.findByName(name);
        return roleEntityCandidate.map(r -> new Role(r.getId(), r.getName()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return roleRepository.findAll().stream()
                .map(r -> new Role(r.getId(), r.getName()))
                .toList();
    }

    @Override
    public List<Role> findAllByNames(List<String> names) {
        return roleRepository.findAllByNameIn(names).stream()
                .map(r -> new Role(r.getId(), r.getName()))
                .toList();
    }

}
