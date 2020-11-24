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
    public boolean unreportPost(String id, String token) throws InterruptedException, ExecutionException {
        if (!isAdmin(token)) return false;
        Post post = firebaseCrud.getById("Publicaciones", id).toObject(Post.class);
        post.setReportado(false);
        return firebaseCrud.update(id, "Publicaciones", post);
    }

    @Override
    public boolean unreportProfile(String id, String token) throws InterruptedException, ExecutionException {
        if (!isAdmin(token)) return false;
        User user = firebaseCrud.getById("Persona", id).toObject(User.class);
        user.setReportado(false);
        return firebaseCrud.update(id, "Persona", user);
    }

    @Override
    public List<Person> getReportedUsers(String token) throws InterruptedException, ExecutionException {
        if (!isAdmin(token)) return null;
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
    public List<Post> getReportedPosts(String token) throws InterruptedException, ExecutionException {
        if (!isAdmin(token)) return null;
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
    public boolean deletePost(String id, String token) throws InterruptedException, ExecutionException {
        if (!isAdmin(token)) return false;
        firebaseCrud.getCollection("Publicaciones").document(id).delete();
        Query feeds = firebaseCrud.collectionGroupSearch("Feed", "id", id);
        ApiFuture<QuerySnapshot> querySnapshot = feeds.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            document.getReference().delete();
        }
        return true;
    }

    @Override
    public boolean deleteProfile(String id, String token) throws InterruptedException, ExecutionException {
        if (!isAdmin(token)) return false;
        CollectionReference collection = firebaseCrud.getSubCollection("Persona", id, "Feed");
        firebaseCrud.deleteCollection(collection, 10);
        firebaseCrud.getCollection("Persona").document(id).delete();
        Query requestsQuery = firebaseCrud.getCollection("Amigos").whereEqualTo("persona1", id);
        ApiFuture<QuerySnapshot> querySnapshot = requestsQuery.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            document.getReference().delete();
        }
        requestsQuery = firebaseCrud.getCollection("Amigos").whereEqualTo("persona2", id);
        querySnapshot = requestsQuery.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            document.getReference().delete();
        }
        requestsQuery = firebaseCrud.collectionGroupSearch("Miembros", "persona_id", id);
        querySnapshot = requestsQuery.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            document.getReference().delete();
        }
        requestsQuery = firebaseCrud.getCollection("Comentarios").whereEqualTo("person.persona_id", id);
        querySnapshot = requestsQuery.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            document.getReference().delete();
        }
        requestsQuery = firebaseCrud.getCollection("JuegosFavoritos").whereEqualTo("person.persona_id", id);
        querySnapshot = requestsQuery.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            document.getReference().delete();
        }
        requestsQuery = firebaseCrud.getCollection("Publicaciones").whereEqualTo("person.persona_id", id);
        querySnapshot = requestsQuery.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            deletePost(document.getId(), token);
        }
        requestsQuery = firebaseCrud.getCollection("Reacciones").whereEqualTo("persona_id", id);
        querySnapshot = requestsQuery.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            document.getReference().delete();
        }
        requestsQuery = firebaseCrud.getCollection("Solicitudes").whereEqualTo("person.persona_id", id);
        querySnapshot = requestsQuery.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            document.getReference().delete();
        }
        requestsQuery = firebaseCrud.collectionGroupSearch("Integrantes", "persona_id", id);
        querySnapshot = requestsQuery.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            document.getReference().delete();
        }
        return true;
    }

    @Override
    public boolean isAdmin(String token) throws InterruptedException, ExecutionException {
        DocumentSnapshot doc = firebaseCrud.getById("Administradores", firebaseSecAuth.getUid(token));
        return doc.exists();
    }
}
