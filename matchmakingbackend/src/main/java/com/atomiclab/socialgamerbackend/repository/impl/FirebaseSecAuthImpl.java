package com.atomiclab.socialgamerbackend.repository.impl;

import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.repository.FirebaseSecAuth;
import com.atomiclab.socialgamerbackend.service.FirebaseService;
import com.google.api.core.ApiFuture;
import com.google.firebase.auth.FirebaseToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirebaseSecAuthImpl implements FirebaseSecAuth {
    @Autowired
    FirebaseService firebaseService;

    private FirebaseToken verifyIdToken(String token) throws InterruptedException, ExecutionException {
        ApiFuture<FirebaseToken> task = firebaseService.getFirebasAuth().verifyIdTokenAsync(token);
        return task.get();
    }
    @Override
    public String getUid(String token) throws InterruptedException, ExecutionException {
        return verifyIdToken(token).getUid();
    }
    @Override
    public String getEmail(String token) throws InterruptedException, ExecutionException {
        return verifyIdToken(token).getEmail();
    }
}
