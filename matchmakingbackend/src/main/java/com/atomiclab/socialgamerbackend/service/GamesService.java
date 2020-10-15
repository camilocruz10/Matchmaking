package com.atomiclab.socialgamerbackend.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Games;
import org.springframework.stereotype.Service;

@Service
public interface GamesService {
    public List<Games> getGames() throws InterruptedException, ExecutionException;

    public boolean setGames(String token, List<Games> favorites) throws InterruptedException, ExecutionException;

	public List<Games> getFavorites(String correo) throws InterruptedException, ExecutionException;
}
