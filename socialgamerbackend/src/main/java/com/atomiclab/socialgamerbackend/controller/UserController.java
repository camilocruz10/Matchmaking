package com.atomiclab.socialgamerbackend.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.User;
import com.atomiclab.socialgamerbackend.service.UserService;
import com.google.firebase.auth.FirebaseAuthException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public boolean register(@RequestBody User user) throws FirebaseAuthException {
        return userService.register(user);
    }
    @PutMapping("/update")
    public boolean updateProfile(@RequestBody User user) {
        return userService.updateProfile(user);
    }
    @GetMapping("/profile/{id}")
    public User getUser(String id) {
        return null;
    }
    @GetMapping("/friends")
    public List<User> getFriends() {
        return null;
    }
    @GetMapping("/list")
    public List<User> getAllUsers() throws InterruptedException, ExecutionException {
        return userService.getAllUsers();
    }
    public String delete(String id) {
        return null;
    }
}
