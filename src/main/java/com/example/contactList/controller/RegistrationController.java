package com.example.contactList.controller;

import com.example.contactList.domain.User;
import com.example.contactList.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseBody
    public boolean addUser(@RequestBody User user) {
        return userService.addUser(user);
    }
}
