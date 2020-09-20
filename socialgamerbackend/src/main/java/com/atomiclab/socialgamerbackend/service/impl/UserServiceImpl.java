package com.atomiclab.socialgamerbackend.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.User;
import com.atomiclab.socialgamerbackend.repository.FirebaseCrud;
import com.atomiclab.socialgamerbackend.repository.FirebaseSecAuth;
import com.atomiclab.socialgamerbackend.service.UserService;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.auth.FirebaseAuthException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    FirebaseCrud firebaseCrud;
    @Autowired
    FirebaseSecAuth firebaseSecAuth;

    @Override
    public boolean register(User user) throws FirebaseAuthException {
        firebaseSecAuth.registerUser(user.getCorreo(), user.getContrasena());
        return firebaseCrud.save(user.getCorreo(), "Persona", user);
    }
    @Override
    public boolean updateProfile(User user) {
        return firebaseCrud.update(user.getCorreo(), "Persona", user);
    }
    @Override
    public User getUser(String id) {
        return null;
    }
    @Override
    public List<User> getFriends(){
        return null;
    }
    @Override
    public List<User> getAllUsers() throws InterruptedException, ExecutionException {
        List<User> users = new ArrayList<User>();
        for(DocumentSnapshot user : firebaseCrud.get("Persona")){
            users.add(user.toObject(User.class));
        }
        return users;
    }
    @Override
    public String delete(String id) {
        return null;
    }
    @Override
    public boolean login(String email, String password) {
        return false;
    }
}
