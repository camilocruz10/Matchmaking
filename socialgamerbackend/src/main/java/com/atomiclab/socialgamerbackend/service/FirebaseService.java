package com.atomiclab.socialgamerbackend.service;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;

public interface FirebaseService {
    public FirebaseAuth getFirebaseAuth();
    public Firestore getFirestore();
}
