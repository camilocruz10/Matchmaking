package com.atomiclab.socialgamerbackend.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.User;
import com.google.firebase.auth.FirebaseAuthException;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public boolean register(User user) throws FirebaseAuthException ;
    public boolean login(String email, String password);
    public boolean updateProfile(User user);
    public User getUser(String id);
    public List<User> getFriends();
    public List<User> getAllUsers() throws InterruptedException, ExecutionException;
    public String delete(String id);
}
