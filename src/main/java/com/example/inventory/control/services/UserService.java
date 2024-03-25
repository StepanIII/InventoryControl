package com.example.inventory.control.services;

import com.example.inventory.control.domain.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    /**
     * Сохранить пользователя.
     *
     * @param user сохраняемый пользователь.
     * @return сохраненный пользователь.
     */
    User save(User user);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);
}
