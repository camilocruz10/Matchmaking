package com.atomiclab.socialgamerbackend.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.User;
import com.atomiclab.socialgamerbackend.repository.FirebaseCrud;
import com.atomiclab.socialgamerbackend.repository.FirebaseSecAuth;
import com.atomiclab.socialgamerbackend.service.FirebaseService;
import com.atomiclab.socialgamerbackend.service.UserService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.auth.FirebaseToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    FirebaseCrud firebaseCrud;
    @Autowired
    FirebaseSecAuth firebaseSecAuth;
    @Autowired
    FirebaseService firebaseService;

    @Override
    public boolean register(User user) { 
        return firebaseCrud.save(user.getCorreo(), "Persona", user);
    }
    @Override
    public boolean updateProfile(User user, String token) throws InterruptedException, ExecutionException {
        ApiFuture <FirebaseToken> task = firebaseService.getFirebasAuth().verifyIdTokenAsync(token);
        FirebaseToken firebaseToken = task.get();
        return firebaseCrud.update(firebaseToken.getEmail(), "Persona", user);
    }
    @Override
    public User getUser(String id) throws InterruptedException, ExecutionException {
        return firebaseCrud.getById("Persona", id).toObject(User.class);
    }
    @Override
    public List<User> getFriends() {
        return null;
    }
    @Override
    public List<User> getAllUsers() throws InterruptedException, ExecutionException {
        List<User> users = new ArrayList<User>();
        for (DocumentSnapshot user : firebaseCrud.get("Persona")) {
            users.add(user.toObject(User.class));
        }

        return users;
    }
    @Override
    public String delete(String id) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public boolean login(String email, String password) {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public User getUserByToken(String token) throws InterruptedException, ExecutionException {
        ApiFuture <FirebaseToken> task = firebaseService.getFirebasAuth().verifyIdTokenAsync(token);
        FirebaseToken firebaseToken = task.get();
        return firebaseCrud.getById("Persona", firebaseToken.getEmail() ).toObject(User.class);
    }
}
