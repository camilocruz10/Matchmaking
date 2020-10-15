package com.atomiclab.socialgamerbackend.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Person;
import com.atomiclab.socialgamerbackend.domain.model.Post;
import com.atomiclab.socialgamerbackend.service.FirebaseService;
import com.atomiclab.socialgamerbackend.service.UserManageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/play")
public class UserManageController {
    @Autowired
    UserManageService userManageService;
    @Autowired
    FirebaseService firebaseAuth;

    @PutMapping("/manage/users/unreport/{id:.+}")
    public boolean unreport(@PathVariable String id) throws InterruptedException, ExecutionException {  
        return userManageService.unreportProfile(id);
    }
    @GetMapping("/manage/users")
    public List<Person> getReportedUsers() throws InterruptedException, ExecutionException {
        return userManageService.getReportedUsers();
    }
    @GetMapping("/manage/posts")
    public List<Post> getReportedPosts() throws InterruptedException, ExecutionException{
        return userManageService.getReportedPosts();
    }
    @PutMapping("/manage/posts/unreport/{id:.+}")
    public boolean unreportPost(@PathVariable String id) throws InterruptedException, ExecutionException {  
        return userManageService.unreportPost(id);
    }
    @DeleteMapping("/manage/posts/delete/{id:.+}")
    public boolean deletePost(@PathVariable String id) throws InterruptedException, ExecutionException {  
        return userManageService.deletePost(id);
    }
    @DeleteMapping("/manage/users/delete/{id:.+}")
    public boolean deleteUser(@PathVariable String id) throws InterruptedException, ExecutionException {  
        return userManageService.deleteProfile(id);
    }
}