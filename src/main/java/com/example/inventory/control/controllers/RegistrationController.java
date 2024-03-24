package com.example.inventory.control.controllers;

import com.example.inventory.control.api.user.UserRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @GetMapping
    public String viewRegistrationPage(Model model) {
        model.addAttribute("userRequest", new UserRequest());
        return "registration";
    }

}
