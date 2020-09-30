package com.atomiclab.socialgamerbackend.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.User;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public boolean register(User user);
    public boolean login(String email, String password);
    public boolean updateProfile(User user, String token) throws InterruptedException, ExecutionException;
    public User getUser(String id) throws InterruptedException, ExecutionException;
    public List<User> getFriends();
    public List<User> getAllUsers() throws InterruptedException, ExecutionException;
    public String delete(String id);
	public User getUserByToken(String token) throws InterruptedException, ExecutionException;
}
