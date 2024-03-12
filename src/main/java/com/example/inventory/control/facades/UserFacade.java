package com.example.inventory.control.facades;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.user.UserRequest;

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

}
