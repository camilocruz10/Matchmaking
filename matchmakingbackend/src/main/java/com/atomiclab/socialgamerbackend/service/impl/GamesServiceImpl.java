package com.atomiclab.socialgamerbackend.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Favorites;
import com.atomiclab.socialgamerbackend.domain.model.Games;
import com.atomiclab.socialgamerbackend.domain.model.Person;
import com.atomiclab.socialgamerbackend.repository.FirebaseCrud;
import com.atomiclab.socialgamerbackend.repository.FirebaseSecAuth;
import com.atomiclab.socialgamerbackend.service.GamesService;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GamesServiceImpl implements GamesService {
    @Autowired
    FirebaseCrud firebaseCrud;
    @Autowired
    FirebaseSecAuth firebaseSecAuth;

    @Override
    public List<Games> getGames() throws InterruptedException, ExecutionException {
        List<Games> gamesList = new ArrayList<Games>();
        for (DocumentSnapshot document : firebaseCrud.get("Juego")) {
            gamesList.add(document.toObject(Games.class));
        }
        return gamesList;
    }

    @Override
    public boolean setGames(String token, List<Games> favorites) throws InterruptedException, ExecutionException {
        String correo = firebaseSecAuth.getEmail(token);
        Person person = firebaseCrud.getById("Persona", correo).toObject(Person.class);
        person.setPersona_id(correo);
        Favorites favorite = new Favorites();
        favorite.setPerson(person);
        for (Games game : favorites) {
            favorite.setGame(game);
            firebaseCrud.save("JuegosFavoritos", favorite);
        }
        return true;
    }

    @Override
    public List<Games> getFavorites(String correo) throws InterruptedException, ExecutionException {
       System.out.println(correo);
        List<Games> games = new ArrayList<Games>();
        CollectionReference collection = firebaseCrud.getCollection("JuegosFavoritos");
        Query requestsQuery = collection.whereEqualTo("person.persona_id", correo);
        for(DocumentSnapshot document : requestsQuery.get().get().getDocuments()){
            games.add(document.toObject(Favorites.class).getGame());
        }
        return games;
    }

}
