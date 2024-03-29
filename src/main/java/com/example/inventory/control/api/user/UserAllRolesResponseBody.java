package com.example.inventory.control.api.user;

import com.example.inventory.control.api.BaseResponseBody;

import java.util.List;

/**
 * Тело ответа на запрос получения всех пользователей.
 */
public class UserAllRolesResponseBody extends BaseResponseBody {

    private List<String> roles;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
