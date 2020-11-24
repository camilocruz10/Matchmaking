package com.atomiclab.socialgamerbackend.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Person;
import com.atomiclab.socialgamerbackend.domain.model.Post;
import com.atomiclab.socialgamerbackend.domain.model.User;
import com.atomiclab.socialgamerbackend.repository.FirebaseCrud;
import com.atomiclab.socialgamerbackend.repository.FirebaseSecAuth;
import com.atomiclab.socialgamerbackend.repository.FirebaseStorage;
import com.atomiclab.socialgamerbackend.service.UserManageService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Este servicio maneja la lógica relacionada a la gestión de usuarios dentro del sistema, solo los administradores pueden acceder a sus funcionalidades 
 * @author Atomic Lab
 * @version 1.0
 */
@Service
public class UserManageServiceImpl implements UserManageService {
    @Autowired
    FirebaseCrud firebaseCrud;
    @Autowired
    FirebaseSecAuth firebaseSecAuth;
    @Autowired
    FirebaseStorage firebaseStorage;
    /**
     * Recibe la identificación del post reportado y el token de autenticación del usuario, con esto valida que sea un administrador y cambia el estado del post a no reportado.
     * @param id identificador del post que se quiere cancelar el reporte
     * @return boolean de si el metodo se realizo con exito
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public boolean unreportPost(String id) throws InterruptedException, ExecutionException {
        Post post = firebaseCrud.getById("Publicaciones", id).toObject(Post.class);
        post.setReportado(false);
        return firebaseCrud.update(id, "Publicaciones", post);
    }
    /**
     * Recibe la identificación del usuario reportado y el token de autenticación del usuario, con esto valida que sea un administrador y cambia el estado del usuario a no reportado.
     * @param id identificación del usuario reportado
     * @return boolean de si el metodo se realizo con exito
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public boolean unreportProfile(String id) throws InterruptedException, ExecutionException {
        User user = firebaseCrud.getById("Persona", id).toObject(User.class);
        user.setReportado(false);
        return firebaseCrud.update(id, "Persona", user);
    }
    /**
     * Recibe el token de autenticación del usuario, con esto valida que administrador y retorna toda la lista de usuarios reportados
     * @return Lista de personas reportadas
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public List<Person> getReportedUsers() throws InterruptedException, ExecutionException {
        List<Person> persons = new ArrayList<Person>();
        Person persona = new Person();
        CollectionReference collection = firebaseCrud.getCollection("Persona");
        Query requestsQuery = collection.whereEqualTo("reportado", true);
        ApiFuture<QuerySnapshot> querySnapshot = requestsQuery.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            persona = document.toObject(Person.class);
            persona.setPersona_id(document.getId());
            persons.add(persona);
        }
        return persons;
    }
    /**
     * Recibe el token de autenticación del usuario, con esto valida que administrador y retorna toda la lista de posts reportados
     * @return Lista de posts
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public List<Post> getReportedPosts() throws InterruptedException, ExecutionException {
        List<Post> posts = new ArrayList<Post>();
        CollectionReference collection = firebaseCrud.getCollection("Publicaciones");
        Query requestsQuery = collection.whereEqualTo("reportado", true);
        ApiFuture<QuerySnapshot> querySnapshot = requestsQuery.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            posts.add(document.toObject(Post.class));
        }
        return posts;
    }
    /**
     * Recibe la identificación del post y el token de autenticación del usuario, con esto valida que sea un administrador y elimina el post de la colección de publicaciones y de todas las sub-colecciones Feed que hay en el sistema. 
     * @param id identificacion del post a eliminar
     * @return boolean de si el metodo se realizo con exito
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public boolean deletePost(String id) throws InterruptedException, ExecutionException {
        ApiFuture<WriteResult> writeResult = firebaseCrud.getCollection("Publicacines").document(id).delete();
        if (writeResult.get().getUpdateTime() != null){
            return true;
        }
        return false;
    }
    /**
     * Recibe la identificación del usuario reportado y el token de autenticación del usuario, con esto valida que sea un administrador y elimina al usuario de la colección de usuarios, toda la información relacionada a él, como amigos, likes, etc y de todas las publicaciones del usuario. 
     * @param id identificación del perfil que se va a eliminar
     * @return boolean que retorna si el metodo funciono o no
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public boolean deleteProfile(String id) throws InterruptedException, ExecutionException {
        ApiFuture<WriteResult> writeResult = firebaseCrud.getCollection("Persona").document(id).delete();
        if (writeResult.get().getUpdateTime() != null){
            return true;
        }
        return false;
    }

    

}
