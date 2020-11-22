package com.atomiclab.socialgamerbackend.service.impl;

import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Games;
import com.atomiclab.socialgamerbackend.repository.FirebaseCrud;
import com.atomiclab.socialgamerbackend.repository.FirebaseSecAuth;
import com.atomiclab.socialgamerbackend.repository.FirebaseStorage;
import com.atomiclab.socialgamerbackend.service.GameManageService;
import com.google.cloud.firestore.DocumentSnapshot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameManageServiceImpl implements GameManageService {
    @Autowired
    FirebaseCrud firebaseCrud;
    @Autowired
    FirebaseSecAuth firebaseSecAuth;
    @Autowired
    FirebaseStorage firebaseStorage;

    @Override
    public boolean createGame(Games game, String token) throws InterruptedException, ExecutionException {
        if (!isAdmin(token)) return false;
        return firebaseCrud.save("Juego", game);
    }

    @Override
    public boolean deleteGame(String name, String image, String token) throws InterruptedException, ExecutionException {
        if (!isAdmin(token)) return false;
        boolean deleteJuego = false, deleteImage = false;
        String id = firebaseCrud.getIdWithUniqueField(name, "Juego");
        if (id != null) {
            deleteJuego = firebaseCrud.delete(id, "Juego");
        }
        if (deleteJuego == true) {
            deleteImage = firebaseStorage.deleteFile(image);
        }
        return deleteImage;
    }

    @Override
    public boolean updateGame(String Oldname, Games newGame, String token) throws InterruptedException, ExecutionException {
        if (!isAdmin(token)) return false;
        boolean valor = false;
        String id = firebaseCrud.getIdWithUniqueField(Oldname, "Juego");
        if (id != null) {
            valor = firebaseCrud.update(id, "Juego", newGame);
        }
        return valor;
    }

    @Override
    public Games getGame(String name, String token) throws InterruptedException, ExecutionException {
        if (!isAdmin(token)) return null;
        Games game = new Games();
        String id = firebaseCrud.getIdWithUniqueField(name, "Juego");
        if (id != null) {
            game = firebaseCrud.getById("Juego", id).toObject(Games.class);
        }
        return game;
    }

    @Override
    public boolean isAdmin(String token) throws InterruptedException, ExecutionException {
        DocumentSnapshot doc = firebaseCrud.getById("Administradores", firebaseSecAuth.getUid(token));
        return doc.exists();
    }
}