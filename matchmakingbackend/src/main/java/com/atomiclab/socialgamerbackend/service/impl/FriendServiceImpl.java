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
/**
 * Este servicio se encarga de manejar la lógica relacionada con la gestión de solicitudes de amistad
 * @author Atomic Lab
 * @version 1.0
 */
@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    FirebaseCrud firebaseCrud;
    @Autowired
    FirebaseSecAuth firebaseSecAuth;
    /**
     * busca las solicitudes de amistad que ha recibido ese usuario
     * @param token String con el token del usuario
     * @return Lista de las personas con las solicitudes
     * @throws InterruptedException
     * @throws ExecutionException 
     */
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
    /**
     * Metodo que verifica si una persona es amigo o no
     * @param token String con el token del usuario
     * @param friendId Identificador del amigo
     * @return boolean dependiendo de si existe registro de que sean amigos en la base de datos.
     * @throws InterruptedException
     * @throws ExecutionException 
     */
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
    /**
     * Verifica si un usuario le mando una solicitud o no
     * @param token String con el token del usuario
     * @param friendId Identificador del amigo
     * @return boolean dependiendo de si existe registro de que sean amigos en la base de datos.
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public boolean isRequestSend(String token, String friendId) throws InterruptedException, ExecutionException {
        Query requestsQuery = firebaseCrud.getCollection("Solicitudes")
            .whereEqualTo("person.persona_id", firebaseSecAuth.getEmail(token)).
            whereEqualTo("receptor_id", friendId);
        ApiFuture<QuerySnapshot> querySnapshot = requestsQuery.get();
        return !querySnapshot.get().getDocuments().isEmpty();
    }
    /**
     * esto acepta la solicitud de amistad del otro usuario añadiendo un objeto Friend a la colección de amigos
     * @param token String con el token del usuario
     * @param friendId Identificador del amigo
     * @return boolean de si se realizo el metodo con exito o no
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public boolean addFriend(String token, String friendId) throws InterruptedException, ExecutionException {
        Query requestsQuery = firebaseCrud.getCollection("Solicitudes")
            .whereEqualTo("person.persona_id", friendId).
            whereEqualTo("receptor_id", firebaseSecAuth.getEmail(token));
        ApiFuture<QuerySnapshot> querySnapshot = requestsQuery.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            firebaseCrud.delete(document.getId(), "Solicitudes");
        }
        Friend friends = new Friend(firebaseSecAuth.getEmail(token), friendId);
        return firebaseCrud.save("Amigos", friends);
    }
    /**
     * Rechazar petición de solicitud de amistad de un amigo
     * @param token String con el token del usuario
     * @param friendId Identificador del amigo
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public void rejectFriend(String token, String friendId) throws InterruptedException, ExecutionException {
        Query requestsQuery = firebaseCrud.getCollection("Solicitudes")
            .whereEqualTo("person.persona_id", friendId).
            whereEqualTo("receptor_id", firebaseSecAuth.getEmail(token));
        ApiFuture<QuerySnapshot> querySnapshot = requestsQuery.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            firebaseCrud.delete(document.getId(), "Solicitudes");
        }
    }
    /**
     * envia la solicitud de amistad al otro usuario añadiendo un objeto Request a la colección de solicitudes 
     * @param token String con el token del usuario
     * @param friendId Identificador del amigo
     * @return boolean de si se realizó con exito o no
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public boolean sendFriendRequest(String token, String friendId) throws InterruptedException, ExecutionException {
        Request request = new Request();
        request.setReceptor_id(friendId);
        Person person = firebaseCrud.getById("Persona", firebaseSecAuth.getEmail(token)).toObject(Person.class);
        person.setPersona_id(firebaseSecAuth.getEmail(token));
        request.setPerson(person);
        return firebaseCrud.save("Solicitudes", request);
    }
    /**
     * elimina el documento que representa dicha amistad de la colección amigos 
     * @param token String con el token del usuario
     * @param friendId Identificador del amigo
     * @return boolean de si se realizó con exito o no
     * @throws InterruptedException
     * @throws ExecutionException 
     */
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
    /**
     * retorna una lista con todos los amigos de esa persona.
     * @param token String con el token del usuario
     * @param id identificadot de la persona
     * @return Lista de personas que son amigos a el
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public List<Person> getFriends(String token, String id) throws InterruptedException, ExecutionException {
        List<Person> friends = new ArrayList<Person>();
        CollectionReference collection = firebaseCrud.getCollection("Amigos");
        Query requestsQuery1 = collection
            .whereEqualTo("persona1", id)
            .select("persona2");
        Query requestsQuery2 = collection
            .whereEqualTo("persona2", id)
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
