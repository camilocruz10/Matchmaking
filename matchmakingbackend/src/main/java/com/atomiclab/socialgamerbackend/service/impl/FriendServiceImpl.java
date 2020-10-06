package com.atomiclab.socialgamerbackend.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Friend;
import com.atomiclab.socialgamerbackend.domain.model.Person;
import com.atomiclab.socialgamerbackend.domain.model.Request;
import com.atomiclab.socialgamerbackend.repository.FirebaseCrud;
import com.atomiclab.socialgamerbackend.repository.FirebaseSecAuth;
import com.atomiclab.socialgamerbackend.service.FriendService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    FirebaseCrud firebaseCrud;
    @Autowired
    FirebaseSecAuth firebaseSecAuth;

    @Override
    public List<Person> getFriendRequests(String token) throws InterruptedException, ExecutionException {
        List<Person> requestsList = new ArrayList<Person>();
        Query requestsQuery = firebaseCrud.getCollection("Solicitudes").whereEqualTo("receptor_id",
                firebaseSecAuth.getEmail(token));
        ApiFuture<QuerySnapshot> querySnapshot = requestsQuery.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            requestsList.add(document.toObject(Request.class).getPerson());
        }
        return requestsList;
    }

    @Override
    public boolean isFriend(String token, String friendId) throws InterruptedException, ExecutionException {
        CollectionReference collection = firebaseCrud.getCollection("Amigos");
        Query requestsQuery1 = collection
            .whereEqualTo("persona1", firebaseSecAuth.getEmail(token))
            .whereEqualTo("persona2", friendId);
        Query requestsQuery2 = collection
            .whereEqualTo("persona2", firebaseSecAuth.getEmail(token))
            .whereEqualTo("persona1", friendId);
        return !requestsQuery1.get().get().isEmpty() || !requestsQuery2.get().get().isEmpty();
    }

    @Override
    public boolean isRequestSend(String token, String friendId) throws InterruptedException, ExecutionException {
        Query requestsQuery = firebaseCrud.getCollection("Solicitudes")
            .whereEqualTo("person.persona_id", firebaseSecAuth.getEmail(token)).
            whereEqualTo("receptor_id", friendId);
        ApiFuture<QuerySnapshot> querySnapshot = requestsQuery.get();
        return !querySnapshot.get().getDocuments().isEmpty();
    }

    @Override
    public boolean addFriend(String token, String friendId) throws InterruptedException, ExecutionException {
        Query requestsQuery = firebaseCrud.getCollection("Solicitudes")
            .whereEqualTo("person.persona_id", firebaseSecAuth.getEmail(token)).
            whereEqualTo("receptor_id", friendId);
        ApiFuture<QuerySnapshot> querySnapshot = requestsQuery.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            firebaseCrud.delete(document.getId(), "Solicitudes");
        }
        Friend friends = new Friend(firebaseSecAuth.getEmail(token), friendId);
        return firebaseCrud.save("Amigos", friends);
    }

    @Override
    public boolean sendFriendRequest(String token, String friendId) throws InterruptedException, ExecutionException {
        Request request = new Request();
        request.setReceptor_id(friendId);
        Person person = firebaseCrud.getById("Persona", firebaseSecAuth.getEmail(token)).toObject(Person.class);
        person.setPersona_id(firebaseSecAuth.getEmail(token));
        request.setPerson(person);
        return firebaseCrud.save("Solicitudes", request);
    }

    @Override
    public boolean deleteFriend(String token, String friendId) throws InterruptedException, ExecutionException {
        CollectionReference collection = firebaseCrud.getCollection("Amigos");
        Query requestsQuery1 = collection
            .whereEqualTo("persona1", firebaseSecAuth.getEmail(token))
            .whereEqualTo("persona2", friendId);
        Query requestsQuery2 = collection
            .whereEqualTo("persona2", firebaseSecAuth.getEmail(token))
            .whereEqualTo("persona1", friendId);
        List<QueryDocumentSnapshot> results = new ArrayList<QueryDocumentSnapshot>();
        results.addAll(requestsQuery1.get().get().getDocuments());
        results.addAll(requestsQuery2.get().get().getDocuments());
        for (DocumentSnapshot document : results) {
            if(!firebaseCrud.delete(document.getId(), "Amigos"))
                return false;
        }
        return true;
    }

    @Override
    public List<Person> getFriends(String token) throws InterruptedException, ExecutionException {
        List<Person> friends = new ArrayList<Person>();
        CollectionReference collection = firebaseCrud.getCollection("Amigos");
        Query requestsQuery1 = collection
            .whereEqualTo("persona1", firebaseSecAuth.getEmail(token))
            .select("persona2");
        Query requestsQuery2 = collection
            .whereEqualTo("persona2", firebaseSecAuth.getEmail(token))
            .select("persona1");
        for(DocumentSnapshot document : requestsQuery1.get().get().getDocuments()){
            Person person = firebaseCrud.getById("Persona", document.getData().get("persona2").toString()).toObject(Person.class);
            person.setPersona_id(document.getData().get("persona2").toString());
            friends.add(person);
        }
        for(DocumentSnapshot document : requestsQuery2.get().get().getDocuments()){
            Person person = firebaseCrud.getById("Persona", document.getData().get("persona1").toString()).toObject(Person.class);
            person.setPersona_id(document.getData().get("persona1").toString());
            friends.add(person);
        }
        return friends;
    }
}
