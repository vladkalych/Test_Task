package com.test_task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class Users {

    @PostMapping("/add")
    public String users(@PathVariable("username") String username, @PathVariable("password") String password){

        return "successRegistered";
    }

}
