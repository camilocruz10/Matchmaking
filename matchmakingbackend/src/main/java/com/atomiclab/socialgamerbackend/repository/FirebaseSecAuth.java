package com.atomiclab.socialgamerbackend.repository;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

@Service
public interface FirebaseSecAuth {
    public String getUid(String token) throws InterruptedException, ExecutionException;
    public String getEmail(String token) throws InterruptedException, ExecutionException;
}
