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
/**
 * Este servicio maneja la lógica relacionada al usuario.
 * @author Atomic Lab
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    FirebaseCrud firebaseCrud;
    @Autowired
    FirebaseSecAuth firebaseSecAuth;
    @Autowired
    FirebaseStorage firebaseStorage;
    /**
     * Recibe un objeto User y lo añade a la base de datos.
     * @param user Objeto de tipo usuario
     * @return boolean que retorna si se realizo correctamente o no
     */
    @Override
    public boolean register(User user) {
        return firebaseCrud.save(user.getCorreo(), "Persona", user);
    }
    /**
     * Recibe un objeto User y el token de autenticación del usuario, con esto actualiza los campos dl usuario relacionados con ese token.
     * @param user Objeto de tipo usuario
     * @param token String con el token del usuario
     * @return boolean que retorna si se realizo correctamente o no
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public boolean updateProfile(User user, String token) throws InterruptedException, ExecutionException {
        return firebaseCrud.update(firebaseSecAuth.getEmail(token), "Persona", user);
    }
    /**
     * Retorna un objeto usuario dependiendo del identificador que recibe.
     * @param id identificador del usuario
     * @return El usuario a buscar
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public User getUser(String id) throws InterruptedException, ExecutionException {
        return firebaseCrud.getById("Persona", id).toObject(User.class);
    }
    /**
     * Retorna la lista de todos los usuarios del sistema.
     * @return La lista de todos los usuarios
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public List<User> getAllUsers() throws InterruptedException, ExecutionException {
        List<User> users = new ArrayList<User>();
        for (DocumentSnapshot user : firebaseCrud.get("Persona")) {
            users.add(user.toObject(User.class));
        }
        return users;
    }
    /**
     * FALTA
     * @param id
     * @return 
     */
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
    /**
     * Retorna un objeto usuario dependiendo del token que recibe.
     * @param token String con el token del usuario
     * @return Retorna ese usuario
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public User getUserByToken(String token) throws InterruptedException, ExecutionException {
        return firebaseCrud.getById("Persona", firebaseSecAuth.getEmail(token)).toObject(User.class);
    }
    /**
     * Recibe una imagen, el nombre de la carpeta donde se debe guardar y sube dicho archivo al Storage
     * @param multipartFile
     * @param folder
     * @return
     * @throws IOException 
     */
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
