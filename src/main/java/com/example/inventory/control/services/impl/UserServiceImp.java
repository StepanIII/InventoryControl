package com.example.inventory.control.services.impl;

import com.example.inventory.control.entities.UserEntity;
import com.example.inventory.control.mapper.UserMapper;
import com.example.inventory.control.repositories.UserRepository;
import com.example.inventory.control.services.UserService;
import com.example.inventory.control.domain.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImp(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь по логину на найден."));
    }

    @Override
    @Transactional
    public User save(User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        userEntity = userRepository.save(userEntity);
        return userMapper.toDomain(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByLogin(String login) {
        Optional<UserEntity> userEntityCandidate = userRepository.findByLogin(login);
        return userEntityCandidate.map(userMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        Optional<UserEntity> userEntityCandidate = userRepository.findById(id);
        return userEntityCandidate.map(userMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDomain)
                .toList();
    }


}
