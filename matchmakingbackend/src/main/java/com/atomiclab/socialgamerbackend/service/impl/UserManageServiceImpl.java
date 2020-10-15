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

@Service
public class UserManageServiceImpl implements UserManageService {
    @Autowired
    FirebaseCrud firebaseCrud;
    @Autowired
    FirebaseSecAuth firebaseSecAuth;
    @Autowired
    FirebaseStorage firebaseStorage;

    @Override
    public boolean unreportPost(String id) throws InterruptedException, ExecutionException {
        Post post = firebaseCrud.getById("Publicaciones", id).toObject(Post.class);
        post.setReportado(false);
        return firebaseCrud.update(id, "Publicaciones", post);
    }

    @Override
    public boolean unreportProfile(String id) throws InterruptedException, ExecutionException {
        User user = firebaseCrud.getById("Persona", id).toObject(User.class);
        user.setReportado(false);
        return firebaseCrud.update(id, "Persona", user);
    }

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

    @Override
    public boolean deletePost(String id) throws InterruptedException, ExecutionException {
        ApiFuture<WriteResult> writeResult = firebaseCrud.getCollection("Publicacines").document(id).delete();
        if (writeResult.get().getUpdateTime() != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteProfile(String id) throws InterruptedException, ExecutionException {
        ApiFuture<WriteResult> writeResult = firebaseCrud.getCollection("Persona").document(id).delete();
        if (writeResult.get().getUpdateTime() != null){
            return true;
        }
        return false;
    }

    

}
