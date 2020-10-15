package com.atomiclab.socialgamerbackend.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Games;
import com.atomiclab.socialgamerbackend.domain.model.Post;
import com.atomiclab.socialgamerbackend.domain.model.User;

import org.springframework.stereotype.Service;

@Service
public interface SearchService {

    public List<User> searchUser(String collectionName, String searchWord)
            throws InterruptedException, ExecutionException;

    public List<Games> searchGames(String collectionName, String searchWord)
            throws InterruptedException, ExecutionException;

    public List<Post> searchPost(String collectionName, String searchWord)
            throws InterruptedException, ExecutionException;
}
