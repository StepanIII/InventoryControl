package com.example.inventory.control.services;

import com.example.inventory.control.domain.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

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

    boolean existsById(Long id);

    /**
     * Получить пользователя по логину.
     *
     * @param login логин пользователя.
     *
     * @return найденный пользователь.
     */
    Optional<User> findByLogin(String login);

    /**
     * Получить пользователя по идентификатору.
     *
     * @param id идентификатор искомого пользователя.
     *
     * @return найденный пользователь.
     */
    Optional<User> findById(Long id);

    /**
     * Получить всех пользователей.
     *
     * @return все пользователи.
     */
    List<User> findAllUsers();

    /**
     * Удалить пользователя по идентификатору.
     *
     * @param id идентификатор пользователя.
     */
    void deleteById(Long id);
}
