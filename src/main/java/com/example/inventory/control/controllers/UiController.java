package com.example.inventory.control.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui")
public class UiController {

    @GetMapping
    public String index() {
        return "index.html";
    }

    @GetMapping("/accept/all")
    public String acceptance() {
        return "acceptance.html";
    }

    @GetMapping("/accept/edit")
    public String acceptanceEdit() {
        return "acceptance-edit.html";
    }

    @GetMapping("/remain/all")
    public String remaining() {
        return "remaining.html";
    }

    @GetMapping("/resource/all")
    public String resources() {
        return "resources.html";
    }

    @GetMapping("/resource/edit")
    public String resourceEdit() {
        return "resource-edit.html";
    }

    @GetMapping("/write-off/all")
    public String writeOff() {
        return "write-off.html";
    }

    @GetMapping("/write-off/edit")
    public String writeOffEdit() {
        return "write-off-edit.html";
    }

}
