package com.atomiclab.socialgamerbackend.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Matchmaking;
import com.atomiclab.socialgamerbackend.domain.model.Person;
import com.atomiclab.socialgamerbackend.repository.FirebaseCrud;
import com.atomiclab.socialgamerbackend.repository.FirebaseSecAuth;
import com.atomiclab.socialgamerbackend.service.MatchmakingService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Es el servicio que se encarga de manejar la lógica de MatchMaking
 * @author Atomic Lab
 * @version 1.0
 */
@Service
public class MatchmakingServiceImpl implements MatchmakingService {
    @Autowired
    FirebaseCrud firebaseCrud;
    @Autowired
    FirebaseSecAuth firebaseSecAuth;
    /**
     * Permite crear el objeto en la base de datos con el usuario
     * @param matchmaking Objeto de tipo matchmaking 
     * @param token String con el token del usuario
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public void create(Matchmaking matchmaking, String token) throws InterruptedException, ExecutionException {
        String correo = firebaseSecAuth.getEmail(token);
        Person person = firebaseCrud.getById("Persona", correo).toObject(Person.class);
        person.setPersona_id(correo);
        matchmaking.setPerson(person);
        firebaseCrud.save("Matchmaking", matchmaking);
    }
    /**
     * filtra todos los match con otros usuarios
     * @param matchmaking Objeto de tipo Matchmaking
     * @return List<Matchmaking> lista con dichos matchs
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public List<Matchmaking> findMatch(Matchmaking matchmaking) throws InterruptedException, ExecutionException {
        List<Matchmaking> matchs = new ArrayList<Matchmaking>();
        List<QueryDocumentSnapshot> results = new ArrayList<QueryDocumentSnapshot>();
        CollectionReference collection = firebaseCrud.getCollection("Matchmaking");
        for (String juego : matchmaking.getJuegos()){
            Query requestsQuery = collection
            .whereArrayContains("juegos", juego)
            .whereEqualTo("region", matchmaking.getRegion());
            results.addAll(requestsQuery.get().get().getDocuments());
        }
        for (DocumentSnapshot document : results) {
            Matchmaking m = document.toObject(Matchmaking.class);
            if(!Collections.disjoint(m.getPlataformas(), matchmaking.getPlataformas())){
                matchs.add(m);
            }
        }
        return matchs;
    }
    /**
     * Eliminar todos los objetos Matchmaking de ese usuario.
     * @param token String con el token del usuario
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public void delete(String token) throws InterruptedException, ExecutionException {
        String correo = firebaseSecAuth.getEmail(token);
        Query requestsQuery = firebaseCrud.getCollection("Matchmaking")
            .whereEqualTo("person.persona_id", correo);
        ApiFuture<QuerySnapshot> querySnapshot = requestsQuery.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            firebaseCrud.delete(document.getId(), "Matchmaking");
        }
    }
}
