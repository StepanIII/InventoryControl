package com.example.inventory.control.api.user;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.user.model.UserModel;
import com.example.inventory.control.api.user.model.UserWithRolesModel;

public class UserWithRolesResponseBody extends BaseResponseBody {

    private UserWithRolesModel user;

    public UserWithRolesModel getUser() {
        return user;
    }

    public void setUser(UserWithRolesModel user) {
        this.user = user;
    }
}
