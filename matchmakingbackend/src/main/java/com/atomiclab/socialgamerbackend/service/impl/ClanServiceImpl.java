package com.atomiclab.socialgamerbackend.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Clan;
import com.atomiclab.socialgamerbackend.domain.model.Person;
import com.atomiclab.socialgamerbackend.domain.model.Post;
import com.atomiclab.socialgamerbackend.repository.FirebaseCrud;
import com.atomiclab.socialgamerbackend.repository.FirebaseSecAuth;
import com.atomiclab.socialgamerbackend.service.ClanService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.Query.Direction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClanServiceImpl implements ClanService {
    @Autowired
    FirebaseCrud firebaseCrud;
    @Autowired
    FirebaseSecAuth firebaseSecAuth;

    @Override
    public boolean createClan(Clan clan, String token) throws InterruptedException, ExecutionException {
        String correo = firebaseSecAuth.getEmail(token);
        Person person = firebaseCrud.getById("Persona", correo).toObject(Person.class);
        person.setPersona_id(correo);
        clan.setPerson(person);
        firebaseCrud.save(clan.getNombre_clan(), "Clanes", clan);
        return firebaseCrud.saveSubCollection("Clanes", clan.getNombre_clan(),"Miembros", person);
    }

    @Override
    public List<Person> getMembers(String nombre_clan) throws InterruptedException, ExecutionException {
        List<Person> members = new ArrayList<Person>();
        CollectionReference clansRef = firebaseCrud.getSubCollection("Clanes", nombre_clan, "Miembros");
        ApiFuture<QuerySnapshot> querySnapshot = clansRef.get();
        for (DocumentSnapshot document2 : querySnapshot.get().getDocuments()) {
            members.add(document2.toObject(Person.class));
        }
        return members;
    }

    @Override
    public List<Post> getPosts(String nombre_clan, String token) throws InterruptedException, ExecutionException {
        if(!isMember(token, nombre_clan)) return null;
        List<Post> feed = new ArrayList<Post>();
        CollectionReference feedRef = firebaseCrud.getSubCollection("Clanes", nombre_clan, "Feed");
        Query feedQuery = feedRef.orderBy("fecha", Direction.DESCENDING).limit(20);
        ApiFuture<QuerySnapshot> querySnapshot = feedQuery.get();
        for (DocumentSnapshot document2 : querySnapshot.get().getDocuments()) {
            feed.add(document2.toObject(Post.class));
        }
        return feed;
    }

    @Override
    public boolean addMember(Person person, String nombre_clan, String token) throws InterruptedException, ExecutionException {
        //rejectRequest validate if isAdmin
        if(!rejectRequest(person.getPersona_id(), nombre_clan, token)) return false;
        return firebaseCrud.saveSubCollection("Clanes", nombre_clan, "Miembros", person);
    }

    @Override
    public boolean addPost(Post post, String nombre_clan, String token) throws InterruptedException, ExecutionException {
        if(!isMember(token, nombre_clan)) return false;
        String correo = firebaseSecAuth.getEmail(token);
        Person person = firebaseCrud.getById("Persona", correo).toObject(Person.class);
        String id = correo.concat(LocalDateTime.now().toString());
        person.setPersona_id(correo);
        post.setPerson(person);
        post.setFecha(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        post.setReportado(false);
        post.setId(id);
        return firebaseCrud.saveSubCollection("Clanes", nombre_clan, "Feed", post);
    }

    @Override
    public List<Clan> getClans() throws InterruptedException, ExecutionException {
        List<Clan> clans = new ArrayList<Clan>();
        CollectionReference clansRef = firebaseCrud.getCollection("Clanes");
        ApiFuture<QuerySnapshot> querySnapshot = clansRef.get();
        for (DocumentSnapshot document2 : querySnapshot.get().getDocuments()) {
            clans.add(document2.toObject(Clan.class));
        }
        return clans;
    }

    @Override
    public List<Clan> getMyClans(String token) throws InterruptedException, ExecutionException {
        String correo = firebaseSecAuth.getEmail(token);
        List<Clan> clans = new ArrayList<Clan>();
        return clans;
    }

    @Override
    public List<Person> getRequests(String nombre_clan, String token) throws InterruptedException, ExecutionException {
        if(!isAdmin(token, nombre_clan)) return null;
        List<Person> requests = new ArrayList<Person>();
        CollectionReference clansRef = firebaseCrud.getSubCollection("Clanes", nombre_clan, "Solicitudes");
        ApiFuture<QuerySnapshot> querySnapshot = clansRef.get();
        for (DocumentSnapshot document2 : querySnapshot.get().getDocuments()) {
            requests.add(document2.toObject(Person.class));
        }
        return requests;
    }

    @Override
    public boolean rejectRequest(String person_id, String nombre_clan, String token) throws InterruptedException, ExecutionException {
        if(!isAdmin(token, nombre_clan)) return false;
        CollectionReference clansRef = firebaseCrud.getSubCollection("Clanes", nombre_clan, "Solicitudes");
        ApiFuture<QuerySnapshot> querySnapshot = clansRef.whereEqualTo("persona_id", person_id).get();
        for (DocumentSnapshot document2 : querySnapshot.get().getDocuments()) {
            document2.getReference().delete();
        }
        return true;
    }

    @Override
    public void deleteMember(String person_id, String nombre_clan, String token) throws InterruptedException, ExecutionException {
        if(!isAdmin(token, nombre_clan)) return;
        CollectionReference clansRef = firebaseCrud.getSubCollection("Clanes", nombre_clan, "Miembros");
        ApiFuture<QuerySnapshot> querySnapshot = clansRef.whereEqualTo("persona_id", person_id).get();
        for (DocumentSnapshot document2 : querySnapshot.get().getDocuments()) {
            document2.getReference().delete();
        }
    }
    @Override
    public boolean isMember(String token, String nombre_clan) throws InterruptedException, ExecutionException {
        String correo = firebaseSecAuth.getEmail(token);
        CollectionReference clansRef = firebaseCrud.getSubCollection("Clanes", nombre_clan, "Miembros");
        ApiFuture<QuerySnapshot> querySnapshot = clansRef.whereEqualTo("persona_id", correo).get();
        System.out.println(querySnapshot.get().getDocuments().size());
        return querySnapshot.get().getDocuments().isEmpty()? false : true;
    }
    @Override
    public boolean isRequestSend(String token, String nombre_clan) throws InterruptedException, ExecutionException {
        String correo = firebaseSecAuth.getEmail(token);
        CollectionReference clansRef = firebaseCrud.getSubCollection("Clanes", nombre_clan, "Solicitudes");
        ApiFuture<QuerySnapshot> querySnapshot = clansRef.whereEqualTo("persona_id", correo).get();
        return querySnapshot.get().getDocuments().isEmpty()? false : true;
    }

    @Override
    public boolean isAdmin(String token, String nombre_clan) throws InterruptedException, ExecutionException {
        String correo = firebaseSecAuth.getEmail(token);
        DocumentSnapshot clansRef = firebaseCrud.getById("Clanes", nombre_clan);
        Person admin = clansRef.toObject(Clan.class).getPerson();
        System.out.println(admin.getPersona_id());
        return admin.getPersona_id().equals(correo)? true : false;
    }

    @Override
    public boolean addRequests(String nombre_clan, String token) throws InterruptedException, ExecutionException {
        String correo = firebaseSecAuth.getEmail(token);
        Person person = firebaseCrud.getById("Persona", correo).toObject(Person.class);
        person.setPersona_id(correo);
        return firebaseCrud.saveSubCollection("Clanes", nombre_clan, "Solicitudes", person);
    }
}
