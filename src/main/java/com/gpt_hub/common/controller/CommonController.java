package com.gpt_hub.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {

    @GetMapping("/")
    public String mainP() {
        return "main";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
