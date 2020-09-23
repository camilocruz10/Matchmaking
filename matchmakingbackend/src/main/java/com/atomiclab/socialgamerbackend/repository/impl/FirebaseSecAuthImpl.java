package com.atomiclab.socialgamerbackend.repository.impl;

import com.atomiclab.socialgamerbackend.repository.FirebaseSecAuth;
import com.atomiclab.socialgamerbackend.service.FirebaseService;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord.CreateRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirebaseSecAuthImpl implements FirebaseSecAuth {
    @Autowired
    FirebaseService firebaseService;
    @Override
    public void registerUser(String email, String password) throws FirebaseAuthException {
        CreateRequest user = new CreateRequest();
        user.setEmail(email);
        user.setPassword(password);
        firebaseService.getFirebasAuth().createUserAsync(user);
    }
    @Override
    public void logoutUser() {
        // TODO Auto-generated method stub
    }
}
