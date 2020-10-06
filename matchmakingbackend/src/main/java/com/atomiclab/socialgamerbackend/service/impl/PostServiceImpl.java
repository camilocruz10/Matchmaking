package com.atomiclab.socialgamerbackend.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Person;
import com.atomiclab.socialgamerbackend.domain.model.Post;
import com.atomiclab.socialgamerbackend.repository.FirebaseCrud;
import com.atomiclab.socialgamerbackend.repository.FirebaseSecAuth;
import com.atomiclab.socialgamerbackend.service.PostService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.Query.Direction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    FirebaseCrud firebaseCrud;
    @Autowired
    FirebaseSecAuth firebaseSecAuth;
    private void setFeed(Post post, String token) throws InterruptedException, ExecutionException {
        CollectionReference collection = firebaseCrud.getCollection("Amigos");
        Query requestsQuery1 = collection
            .whereEqualTo("persona1", firebaseSecAuth.getEmail(token))
            .select("persona2");
        Query requestsQuery2 = collection
            .whereEqualTo("persona2", firebaseSecAuth.getEmail(token))
            .select("persona1");
        for(DocumentSnapshot document : requestsQuery1.get().get().getDocuments()){
            firebaseCrud.saveSubCollection("Persona", document.getData().get("persona2").toString(), "Feed", post);
        }
        for(DocumentSnapshot document : requestsQuery2.get().get().getDocuments()){
            firebaseCrud.saveSubCollection("Persona", document.getData().get("persona1").toString(), "Feed", post);
        }
    }
    @Override
    public boolean uploadPost(Post post, String token) throws InterruptedException, ExecutionException {
        Person person = firebaseCrud.getById("Persona", firebaseSecAuth.getEmail(token)).toObject(Person.class);
        person.setPersona_id(firebaseSecAuth.getEmail(token));
        post.setPerson(person);
        post.setFecha(Date.from(LocalDateTime.now()
            .atZone(ZoneId.systemDefault())
            .toInstant()));
        post.setReportado(false);
        setFeed(post, token);
        return firebaseCrud.save("Publicaciones", post);
    }

    @Override
    public List<Post> getFeed(String token) throws InterruptedException, ExecutionException {
        List<Post> feed = new ArrayList<Post>();
        CollectionReference feedRef = firebaseCrud.getSubCollection("Persona", firebaseSecAuth.getEmail(token) , "Feed");
        Query feedQuery = feedRef.orderBy("fecha", Direction.DESCENDING).limit(20);
        ApiFuture<QuerySnapshot> querySnapshot = feedQuery.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            feed.add(document.toObject(Post.class));
        }
        return feed;
    }
}
