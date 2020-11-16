package com.atomiclab.socialgamerbackend.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.User;
import com.atomiclab.socialgamerbackend.service.FirebaseService;
import com.atomiclab.socialgamerbackend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/play")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    FirebaseService firebaseAuth;

    @PutMapping("/edit/profile")
    public boolean updateProfile(@RequestBody User user, @RequestHeader("X-Firebase-Auth") String token)
            throws InterruptedException, ExecutionException {
        return userService.updateProfile(user, token);
    }

    @PutMapping("/profile/report/{id:.+}")
    public boolean report(@PathVariable String id) throws InterruptedException, ExecutionException {
        return userService.reportProfile(id);
    }

    @GetMapping("/profile/{id:.+}")
    public User getUser(@PathVariable String id) throws InterruptedException, ExecutionException {
        return userService.getUser(id);
    }

    @GetMapping("/edit/profile")
    public User getUserEditProfile(@RequestHeader("X-Firebase-Auth") String token)
            throws InterruptedException, ExecutionException {
        return userService.getUserByToken(token);
    }

    public String delete(String id) {
        return null;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam("folder") String folder)
            throws IOException {
        return userService.uploadFile(file, folder);
    }

    @GetMapping("/download")
    public String download(@RequestParam(name = "ruta") String filename) throws Exception {
        return userService.downloadFile(filename);
    }

    @PutMapping("/post/report/{id:.+}")
    public boolean reportPost(@PathVariable String id) throws InterruptedException, ExecutionException {
        return userService.reportPost(id);
    }

    @GetMapping("/isAdmin")
    public boolean isAdmin(@RequestHeader("X-Firebase-Auth") String token)
            throws InterruptedException, ExecutionException {
        return userService.isAdmin(token);
    }
}
