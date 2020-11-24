package com.atomiclab.socialgamerbackend.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.ExecutionException;
import com.atomiclab.socialgamerbackend.domain.model.Games;

@Service
public interface GameManageService {
    public boolean createGame(Games game, String token) throws InterruptedException, ExecutionException;
    public boolean deleteGame(String name, String image, String token) throws InterruptedException, ExecutionException;
    public boolean updateGame(String Oldname, Games newGame, String token) throws InterruptedException, ExecutionException;
    public Games getGame(String name, String token) throws InterruptedException, ExecutionException;
    public boolean isAdmin(String token) throws InterruptedException, ExecutionException;
}
