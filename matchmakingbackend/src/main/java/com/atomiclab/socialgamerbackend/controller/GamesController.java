package com.atomiclab.socialgamerbackend.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Games;
import com.atomiclab.socialgamerbackend.service.GamesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/play")
public class GamesController {

    @Autowired
    GamesService gamesService;

    @PostMapping("/games/favorites")
    public boolean setFavorites(@RequestHeader("X-Firebase-Auth") String token, @RequestBody List<Games> games)
            throws InterruptedException, ExecutionException {
        return gamesService.setGames(token, games);
    }

    @GetMapping("/games/favorites/{id:.+}")
    public List<Games> getFavorites(@RequestHeader("X-Firebase-Auth") String token,  @PathVariable String id)
            throws InterruptedException, ExecutionException {
        return gamesService.getFavorites(id);
    }
}
