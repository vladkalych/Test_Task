package com.test_task.controller;

import com.test_task.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping()
    public String about(Model model){
        model.addAttribute("userDto", new UserDto());
        return "login";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("userDto", new UserDto());
        return "login";
    }

}
