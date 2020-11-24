package com.atomiclab.socialgamerbackend.service.impl;

import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Games;
import com.atomiclab.socialgamerbackend.repository.FirebaseCrud;
import com.atomiclab.socialgamerbackend.repository.FirebaseSecAuth;
import com.atomiclab.socialgamerbackend.repository.FirebaseStorage;
import com.atomiclab.socialgamerbackend.service.GameManageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Este servicio maneja la lógica relacionada a la gestión de juegos dentro del sistema, solo los administradores pueden acceder a sus funcionalidades 
 * @author Atomic Lab
 * @version 1.0
 */
@Service
public class GameManageServiceImpl implements GameManageService {
    @Autowired
    FirebaseCrud firebaseCrud;
    @Autowired
    FirebaseSecAuth firebaseSecAuth;
    @Autowired
    FirebaseStorage firebaseStorage;
    /**
     * con esto valida que sea administrador y procede a añadir el nuevo juego a la colección de juegos
     * @param game Objeto de tipo Games
     * @return boolean que retorna si se creo el juego o no
     */
    public boolean createGame(Games game) {
        return firebaseCrud.save("Juego", game);
    }
    /**
     * elimina el juego con su respectiva imagen del Storage. 
     * @param name nombre del juego
     * @param image imagen del juego
     * @return boolean que retorna si se elimino el juego o no
     * @throws InterruptedException
     * @throws ExecutionException 
     */
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
    /**
     * actualiza los campos de un videojuego
     * @param Oldname nombre del juego antiguo
     * @param newGame Objeto de tipo juego
     * @return boolean que retorna si se actualizo el juego o no
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public boolean updateGame(String Oldname, Games newGame) throws InterruptedException, ExecutionException {
        boolean valor = false;
        String id = firebaseCrud.getIdWithUniqueField(Oldname, "Juego");
        if (id != "") {
            valor = firebaseCrud.update(id, "Juego", newGame);
        }
        return valor;
    }
    /**
     * retorna el juego con ese nombre.
     * @param name nombre del juego
     * @return Objeto de tipo juego con ese nombre
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public Games getGame(String name) throws InterruptedException, ExecutionException {
        Games game = new Games();
        String id = firebaseCrud.getIdWithUniqueField(name, "Juego");
        if (id != "") {
            game = firebaseCrud.getById("Juego", id).toObject(Games.class);
        }
        return game;
    }
}