package com.atomiclab.socialgamerbackend.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Person;
import com.atomiclab.socialgamerbackend.domain.model.Post;

import org.springframework.stereotype.Service;

@Service
public interface UserManageService {

	public boolean unreportProfile(String id, String token) throws InterruptedException, ExecutionException;
    public List<Person> getReportedUsers(String token) throws InterruptedException, ExecutionException;
    public List<Post> getReportedPosts(String token) throws InterruptedException, ExecutionException;
    public boolean unreportPost(String id, String token) throws InterruptedException, ExecutionException;
    public boolean deletePost(String id, String token) throws InterruptedException, ExecutionException;
    public boolean deleteProfile(String id, String token) throws InterruptedException, ExecutionException;
    public boolean isAdmin(String token) throws InterruptedException, ExecutionException;
    
}
