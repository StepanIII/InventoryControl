package com.example.inventory.control.api.user;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.user.model.UserModel;

public class UserResponseBody extends BaseResponseBody {

    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
