package com.atomiclab.socialgamerbackend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Clan;
import com.atomiclab.socialgamerbackend.domain.model.Games;
import com.atomiclab.socialgamerbackend.domain.model.Post;
import com.atomiclab.socialgamerbackend.domain.model.Squad;
import com.atomiclab.socialgamerbackend.domain.model.User;
import com.atomiclab.socialgamerbackend.service.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/play")
public class SearchController {
    @Autowired
    SearchService searchService;

    @GetMapping("/search/users/{searchWord}")
    public List<User> searchUser(@PathVariable String searchWord) throws InterruptedException, ExecutionException {
        List<User> personasResult = new ArrayList<>();
        personasResult = searchService.searchUser("Persona", searchWord);
        return personasResult;
    }
    @GetMapping("/search/games/{searchWord}")
    public List<Games> searchGames(@PathVariable String searchWord) throws InterruptedException, ExecutionException {
        List<Games> gamesList = new ArrayList<>();
        gamesList = searchService.searchGames("Juego", searchWord);
        return gamesList;
    }
    @GetMapping("/search/posts/{searchWord}")
    public List<Post> searchPost(@PathVariable String searchWord) throws InterruptedException, ExecutionException {
        List<Post> gamesList = new ArrayList<>();
        gamesList = searchService.searchPost("Publicaciones", searchWord);
        return gamesList;
    }
    @GetMapping("/search/clans/{searchWord}")
    public List<Clan> searchClans(@PathVariable String searchWord) throws InterruptedException, ExecutionException {
        List<Clan> clansList = new ArrayList<>();
        clansList = searchService.searchClan("Clanes", searchWord);
        return clansList;
    }
    @GetMapping("/search/squads/{searchWord}")
    public List<Squad> searchSquad(@PathVariable String searchWord) throws InterruptedException, ExecutionException {
        List<Squad> squadList = new ArrayList<>();
        squadList = searchService.searchSquad("Squad", searchWord);
        return squadList;
    }
}
