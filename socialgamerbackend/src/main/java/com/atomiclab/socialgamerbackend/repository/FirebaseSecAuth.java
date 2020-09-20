package com.atomiclab.socialgamerbackend.repository;

import com.google.firebase.auth.FirebaseAuthException;

import org.springframework.stereotype.Service;

@Service
public interface FirebaseSecAuth {
    public void registerUser(String email, String password) throws FirebaseAuthException;
    public void logoutUser();
}
