package com.atomiclab.socialgamerbackend.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.User;
import com.atomiclab.socialgamerbackend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRegisterController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public boolean register(@RequestBody User user) {
        return userService.register(user);
    }
    @GetMapping("/list")
    public List<User> getAllUsers() throws InterruptedException, ExecutionException {
        System.out.println("HOLA");
        return userService.getAllUsers();
    }
}
