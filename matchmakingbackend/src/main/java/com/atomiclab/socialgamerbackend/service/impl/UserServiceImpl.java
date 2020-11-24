package com.atomiclab.socialgamerbackend.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Post;
import com.atomiclab.socialgamerbackend.domain.model.User;
import com.atomiclab.socialgamerbackend.repository.FirebaseCrud;
import com.atomiclab.socialgamerbackend.repository.FirebaseSecAuth;
import com.atomiclab.socialgamerbackend.repository.FirebaseStorage;
import com.atomiclab.socialgamerbackend.service.UserService;
import com.google.cloud.firestore.DocumentSnapshot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    FirebaseCrud firebaseCrud;
    @Autowired
    FirebaseSecAuth firebaseSecAuth;
    @Autowired
    FirebaseStorage firebaseStorage;

    @Override
    public boolean register(User user) {
        if(user.getFoto_perfil().equals("") || user.getFoto_perfil() == null)
            user.setFoto_perfil("Fotosperfil/default.png");
        return firebaseCrud.save(user.getCorreo(), "Persona", user);
    }

    @Override
    public boolean updateProfile(User user, String token) throws InterruptedException, ExecutionException {
        return firebaseCrud.update(firebaseSecAuth.getEmail(token), "Persona", user);
    }

    @Override
    public User getUser(String id) throws InterruptedException, ExecutionException {
        return firebaseCrud.getById("Persona", id).toObject(User.class);
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
    public User getUserByToken(String token) throws InterruptedException, ExecutionException {
        return firebaseCrud.getById("Persona", firebaseSecAuth.getEmail(token)).toObject(User.class);
    }

    @Override
    public String uploadFile(MultipartFile multipartFile, String folder) throws IOException {
        return firebaseStorage.uploadFile(multipartFile, folder);
    }

    @Override
    public String downloadFile(String file) throws Exception {
        return firebaseStorage.downloadFile(file);
    }

    @Override
    public boolean reportProfile(String id) throws InterruptedException, ExecutionException {
        User user = firebaseCrud.getById("Persona", id).toObject(User.class);
        user.setReportado(true);
        return firebaseCrud.update(id, "Persona", user);
    }

    @Override
    public boolean reportPost(String id) throws InterruptedException, ExecutionException {
        Post post = firebaseCrud.getById("Publicaciones", id).toObject(Post.class);
        post.setReportado(true);
        return firebaseCrud.update(id, "Publicaciones", post);
    }

    @Override
    public boolean isAdmin(String token) throws InterruptedException, ExecutionException {
        DocumentSnapshot doc = firebaseCrud.getById("Administradores", firebaseSecAuth.getUid(token));
        return doc.exists();
    }

}
