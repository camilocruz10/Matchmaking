package com.atomiclab.socialgamerbackend.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.User;
import com.atomiclab.socialgamerbackend.service.FirebaseService;
import com.atomiclab.socialgamerbackend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/play")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    FirebaseService firebaseAuth;

    @PutMapping("/edit/profile")
    public boolean updateProfile(@RequestBody User user, @RequestHeader("X-Firebase-Auth") String token ) throws InterruptedException, ExecutionException {  
        return userService.updateProfile(user, token) ;
    }
    @GetMapping("/profile/{id:.+}")
    public User getUser(@PathVariable String tokenUpdate) throws InterruptedException, ExecutionException {
        return userService.getUser(tokenUpdate);
    }
    @GetMapping("/edit/profile")
    public User getUserEditProfile(@RequestHeader("X-Firebase-Auth") String token )
            throws InterruptedException, ExecutionException{
        return userService.getUserByToken(token);
    }
    @GetMapping("/friends")
    public List<User> getFriends() {
        return null;
    }
    public String delete(String id) {
        return null;
    }
}
