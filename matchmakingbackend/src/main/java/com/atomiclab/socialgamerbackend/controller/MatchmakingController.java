package com.atomiclab.socialgamerbackend.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Matchmaking;
import com.atomiclab.socialgamerbackend.service.MatchmakingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/play")
public class MatchmakingController {
    @Autowired
    MatchmakingService matchmakingService;

    @PostMapping("/matchmaking/create")
    public void create(@RequestBody Matchmaking matchmaking, @RequestHeader("X-Firebase-Auth") String token)
            throws InterruptedException, ExecutionException {
        matchmakingService.create(matchmaking, token);
    }
    @PostMapping("/matchmaking/match")
    public List<Matchmaking> findMatch(@RequestBody Matchmaking matchmaking,  @RequestHeader("X-Firebase-Auth") String token)
            throws InterruptedException, ExecutionException {
        return matchmakingService.findMatch(matchmaking, token);
    }
    @PostMapping("/matchmaking/delete")
    public void delete(@RequestHeader("X-Firebase-Auth") String token) throws InterruptedException, ExecutionException {
        matchmakingService.delete(token);
    }
}