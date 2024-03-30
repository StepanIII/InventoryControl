package com.example.inventory.control.facades;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.user.UserAllRolesResponseBody;
import com.example.inventory.control.api.user.UserCreateAdminRequest;
import com.example.inventory.control.api.user.UserRequest;
import com.example.inventory.control.api.user.UserWithRolesResponseBody;
import com.example.inventory.control.api.user.UsersResponseBody;

/**
 * Фасад для работы с пользователями.
 */
public interface UserFacade {

    /**
     * Добавить пользователя.
     *
     * @param request запрос с данными пользователя.
     *
     * @return ответ со статусом.
     */
    BaseResponseBody addUser(UserRequest request);

    /**
     * Добавить пользователя.
     *
     * @param request запрос с данными пользователя.
     *
     * @return ответ со статусом.
     */
    BaseResponseBody addUser(UserCreateAdminRequest request);

    /**
     * Получить пользователя по логину.
     *
     * @param login логин пользователя.
     *
     * @return ответ со статусом и пользователем.
     */
    UserWithRolesResponseBody getUserByLogin(String login);

    /**
     * Получить всех пользователей.
     *
     * @return найденные пользователи.
     */
    UsersResponseBody getAllUsers();

    /**
     * Получить все роли пользотвателей.
     *
     * @return ответ со статусом и ролями.
     */
    UserAllRolesResponseBody getAllUserRoles();

    /**
     * Получить пользователя по идентификатору.
     *
     * @param id идентификатор искомого пользователя.
     *
     * @return ответ со статусом и пользователем.
     */
    UserWithRolesResponseBody getUserById(Long id);

    /**
     * Обновить пользователя.
     *
     * @param id      идентификатор обновляемого пользователя.
     * @param request запрос с данными.
     *
     * @return ответ со статусом.
     *
     */
    BaseResponseBody updateUser(Long id, UserCreateAdminRequest request);
}
