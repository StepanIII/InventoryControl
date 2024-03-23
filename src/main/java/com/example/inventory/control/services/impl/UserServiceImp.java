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
        return userRepository.findByLogin(username);
    }

    @Override
    @Transactional(readOnly = true)
    public User save(User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        userEntity = userRepository.save(userEntity);
        return userMapper.toDomain(userEntity);
    }
}
