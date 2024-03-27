package com.example.inventory.control.facades;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.user.UserRequest;
import com.example.inventory.control.api.user.UserResponseBody;

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
     * Получить пользователя по логину.
     *
     * @param login логин пользователя.
     *
     * @return ответ со статусом и пользователем.
     */
    UserResponseBody getUserByLogin(String login);
}
