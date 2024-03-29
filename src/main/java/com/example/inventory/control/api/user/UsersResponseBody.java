package com.example.inventory.control.api.user;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.resources.model.ResourceDto;
import com.example.inventory.control.api.user.model.UserAllInfoModel;

import java.util.List;

/**
 * Ответ на запрос получения всех клиентов.
 */
public class UsersResponseBody extends BaseResponseBody {

    /**
     * Ресурсы.
     */
    private List<UserAllInfoModel> users;


    public List<UserAllInfoModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserAllInfoModel> users) {
        this.users = users;
    }
}
