package com.atomiclab.socialgamerbackend.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Clan;
import com.atomiclab.socialgamerbackend.domain.model.Games;
import com.atomiclab.socialgamerbackend.domain.model.Post;
import com.atomiclab.socialgamerbackend.domain.model.Squad;
import com.atomiclab.socialgamerbackend.domain.model.User;
import com.atomiclab.socialgamerbackend.repository.FirebaseCrud;
import com.atomiclab.socialgamerbackend.service.SearchService;
import com.google.cloud.firestore.DocumentSnapshot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Es el servicio que maneja la lógica del buscador del sistema
 * @author Atomic Lab
 * @version 1.0
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    FirebaseCrud firebaseCrud;
    /**
     * Recibe el nombre de la colección en la que se buscará y la palabra por la que se filtrará, finalmente retorna una lista con todas las coincidencias.
     * @param collectionName Nombre de la colección en la que va a buscar
     * @param searchWord Palabra a buscar en la colección
     * @return List<User> Representa la lista de usuarios que se filtran en la busqueda
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public List<User> searchUser(String collectionName, String searchWord)
            throws InterruptedException, ExecutionException {
        List<User> personasResult = new ArrayList<>();
        for (DocumentSnapshot doc : firebaseCrud.getCollection(collectionName).get().get().getDocuments()) {
            User userAux = doc.toObject(User.class);
            if (userAux.getNombre_usuario().toLowerCase().contains(searchWord.toLowerCase().trim())) {
                personasResult.add(userAux);
            }
        }
        return personasResult;
    }
    /**
     * Recibe el nombre de la colección en la que se buscará y la palabra por la que se filtrará, finalmente retorna una lista con todas las coincidencias.
     * @param collectionName Nombre de la colección en la que va a buscar
     * @param searchWord Palabra a buscar en la colección
     * @return List<Games> Representa la lista de juegos que se filtran en la busqueda
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public List<Games> searchGames(String collectionName, String searchWord)
            throws InterruptedException, ExecutionException {
        List<Games> juegosResult = new ArrayList<>();
        ;
        for (DocumentSnapshot doc : firebaseCrud.getCollection(collectionName).get().get().getDocuments()) {
            Games gamesAux = doc.toObject(Games.class);
            if (gamesAux.getNombre().toLowerCase().contains(searchWord.toLowerCase().trim())) {
                juegosResult.add(gamesAux);
            }
        }
        return juegosResult;
    }
    /**
     * Recibe el nombre de la colección en la que se buscará y la palabra por la que se filtrará, finalmente retorna una lista con todas las coincidencias.
     * @param collectionName Nombre de la colección en la que va a buscar
     * @param searchWord Palabra a buscar en la colección
     * @return List<Post> Representa la lista de posts que se filtran en la busqueda
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public List<Post> searchPost(String collectionName, String searchWord)
            throws InterruptedException, ExecutionException {
        List<Post> postResult = new ArrayList<>();
        for (DocumentSnapshot doc : firebaseCrud.getCollection(collectionName).get().get().getDocuments()) {
            Post postAux = doc.toObject(Post.class);
            if (postAux.getContenido().toLowerCase().contains(searchWord.toLowerCase().trim())) {
                postResult.add(postAux);
            }
        }
        return postResult;
    }
    /**
     * Recibe el nombre de la colección en la que se buscará y la palabra por la que se filtrará, finalmente retorna una lista con todas las coincidencias.
     * @param collectionName Nombre de la colección en la que va a buscar
     * @param searchWord Palabra a buscar en la colección
     * @return List<Clan> Representa la lista de clanes que se filtran en la busqueda
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public List<Clan> searchClan(String collectionName, String searchWord)
            throws InterruptedException, ExecutionException {
        List<Clan> clanResult = new ArrayList<>();
        for (DocumentSnapshot doc : firebaseCrud.getCollection(collectionName).get().get().getDocuments()) {
            Clan clanAux = doc.toObject(Clan.class);
            if (clanAux.getNombre_clan().toLowerCase().contains(searchWord.toLowerCase().trim()) && clanAux.isEsPrivado()) {
                clanResult.add(clanAux);
            }
        }
        return clanResult;
    }
    /**
     * Recibe el nombre de la colección en la que se buscará y la palabra por la que se filtrará, finalmente retorna una lista con todas las coincidencias.
     * @param collectionName Nombre de la colección en la que va a buscar
     * @param searchWord Palabra a buscar en la colección
     * @return List<Squad> Representa la lista de squads que se filtran en la busqueda
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public List<Squad> searchSquad(String collectionName, String searchWord)
            throws InterruptedException, ExecutionException {
        List<Squad> squadResult = new ArrayList<>();
        for (DocumentSnapshot doc : firebaseCrud.getCollection(collectionName).get().get().getDocuments()) {
            Squad squadAux = doc.toObject(Squad.class);
            if (squadAux.getNombre().toLowerCase().contains(searchWord.toLowerCase().trim())) {
                squadResult.add(squadAux);
            }
        }
        return squadResult;
    }
}
