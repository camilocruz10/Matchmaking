package com.atomiclab.socialgamerbackend.service.impl;

import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Games;
import com.atomiclab.socialgamerbackend.repository.FirebaseCrud;
import com.atomiclab.socialgamerbackend.repository.FirebaseSecAuth;
import com.atomiclab.socialgamerbackend.repository.FirebaseStorage;
import com.atomiclab.socialgamerbackend.service.GameManageService;

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

    public boolean createGame(Games game) {
        return firebaseCrud.save("Juego", game);
    }

    public boolean deleteGame(String name, String image) throws InterruptedException, ExecutionException {
        boolean deleteJuego = false, deleteImage = false;
        String id = firebaseCrud.getIdWithUniqueField(name, "Juego");
        if (id != "") {
            deleteJuego = firebaseCrud.delete(id, "Juego");
        }
        if (deleteJuego == true) {
            deleteImage = firebaseStorage.deleteFile(image);
        }
        return deleteImage;
    }

    public boolean updateGame(String Oldname, Games newGame) throws InterruptedException, ExecutionException {
        boolean valor = false;
        String id = firebaseCrud.getIdWithUniqueField(Oldname, "Juego");
        if (id != "") {
            valor = firebaseCrud.update(id, "Juego", newGame);
        }
        return valor;
    }

    public Games getGame(String name) throws InterruptedException, ExecutionException {
        Games game = new Games();
        String id = firebaseCrud.getIdWithUniqueField(name, "Juego");
        if (id != "") {
            game = firebaseCrud.getById("Juego", id).toObject(Games.class);
        }
        return game;
    }
}