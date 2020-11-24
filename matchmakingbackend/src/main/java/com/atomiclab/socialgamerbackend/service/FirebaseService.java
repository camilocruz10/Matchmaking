package com.atomiclab.socialgamerbackend.service;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;

import org.springframework.stereotype.Service;

@Service
public interface FirebaseService {
    public FirebaseAuth getFirebasAuth();

    public Firestore getFirestore();
}
