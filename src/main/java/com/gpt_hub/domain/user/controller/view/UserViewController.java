package com.gpt_hub.domain.user.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

    @GetMapping("/signup")
    public String join() {
        return "signup";
    }
}
