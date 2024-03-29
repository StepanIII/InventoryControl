package com.example.inventory.control.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui")
public class UiController {

//    @GetMapping
//    public String index() {
//        return "home.html";
//    }

    @GetMapping("/user/all")
    public String user() {
        return "user.html";
    }

    @GetMapping("/accept/all")
    public String acceptance() {
        return "acceptance.html";
    }

    @GetMapping("/accept/edit")
    public String acceptanceEdit() {
        return "acceptance-edit.html";
    }

    @GetMapping("/issue/all")
    public String issue() {
        return "issue.html";
    }

    @GetMapping("/issue/edit")
    public String issueEdit() {
        return "issue-edit.html";
    }

    @GetMapping("/capitalization/all")
    public String capitalization() {
        return "capitalization.html";
    }

    @GetMapping("/capitalization/edit")
    public String capitalizationEdit() {
        return "capitalization-edit.html";
    }

    @GetMapping("/remain/all")
    public String remaining() {
        return "remaining.html";
    }

    @GetMapping("/resource/all")
    public String resources() {
        return "resources.html";
    }

    @GetMapping("/warehouse/all")
    public String warehouse() {
        return "warehouse.html";
    }

    @GetMapping("/client/all")
    public String client() {
        return "client.html";
    }

    @GetMapping("/write-off/all")
    public String writeOff() {
        return "write-off.html";
    }

    @GetMapping("/write-off/edit")
    public String writeOffEdit() {
        return "write-off-edit.html";
    }

    @GetMapping("/move/all")
    public String move() {
        return "move.html";
    }

    @GetMapping("/move/edit")
    public String moveEdit() {
        return "move-edit.html";
    }

    @GetMapping("/move/show")
    public String moveShow() {
        return "move-show.html";
    }

    @GetMapping("/inventory/all")
    public String inventory() {
        return "inventory.html";
    }

    @GetMapping("/inventory/edit")
    public String inventoryEdit() {
        return "inventory-edit.html";
    }

    @GetMapping("/inventory/show")
    public String inventoryShow() {
        return "inventory-show.html";
    }

}
