package com.atomiclab.socialgamerbackend.service;

import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.User;

import org.springframework.stereotype.Service;

@Service
public interface SearchService {
    public Set<User> search(String collectionName, String searchWord, String fieldName)
            throws InterruptedException, ExecutionException;
}
