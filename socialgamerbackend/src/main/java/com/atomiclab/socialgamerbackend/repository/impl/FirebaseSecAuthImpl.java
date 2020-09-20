package com.atomiclab.socialgamerbackend.repository.impl;

import com.atomiclab.socialgamerbackend.repository.FirebaseSecAuth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord.CreateRequest;

import org.springframework.stereotype.Service;

@Service
public class FirebaseSecAuthImpl implements FirebaseSecAuth {
    @Override
    public void registerUser(String email, String password) throws FirebaseAuthException {
        CreateRequest user = new CreateRequest();
        user.setEmail(email);
        user.setPassword(password);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserAsync(user);
    }
    @Override
    public void logoutUser() {
    }
}
