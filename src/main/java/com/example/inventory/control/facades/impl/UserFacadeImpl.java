package com.example.inventory.control.facades.impl;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.user.UserRequest;
import com.example.inventory.control.facades.UserFacade;
import com.example.inventory.control.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    public UserFacadeImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public BaseResponseBody addUser(UserRequest request) {
        return null;
    }

}
