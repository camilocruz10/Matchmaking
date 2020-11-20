package com.atomiclab.socialgamerbackend.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;
import com.atomiclab.socialgamerbackend.domain.model.Games;
import com.atomiclab.socialgamerbackend.service.FirebaseService;
import com.atomiclab.socialgamerbackend.service.GameManageService;
import com.atomiclab.socialgamerbackend.service.GamesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/play")
public class GameManageController {

    @Autowired
    FirebaseService firebaseAuth;
    @Autowired
    GamesService gamesService;
    @Autowired
    GameManageService gameManageService;

    @PostMapping("games/new")
    public boolean createGame(@RequestBody Games game, @RequestHeader("X-Firebase-Auth") String token)
            throws InterruptedException, ExecutionException {
        return gameManageService.createGame(game, token);
    }

    @DeleteMapping("games/delete")
    public boolean deleteGame(@RequestHeader("gameName") String gameName, @RequestHeader("X-Firebase-Auth") String token, @RequestHeader("image") String imagen)
            throws InterruptedException, ExecutionException {
        return gameManageService.deleteGame(gameName, imagen, token);
    }

    @PutMapping("games/update/{Oldname}")
    public boolean putMethodName(@PathVariable String Oldname, @RequestBody Games newGame, @RequestHeader("X-Firebase-Auth") String token)
            throws InterruptedException, ExecutionException {
        return gameManageService.updateGame(Oldname, newGame, token);
    }

    @GetMapping("games")
    public List<Games> getGames() throws InterruptedException, ExecutionException {
        return gamesService.getGames();
    }

    @GetMapping("games/{name}")
    public Games getGame(@PathVariable String name, @RequestHeader("X-Firebase-Auth") String token) throws InterruptedException, ExecutionException {
        return gameManageService.getGame(name, token);
    }

}