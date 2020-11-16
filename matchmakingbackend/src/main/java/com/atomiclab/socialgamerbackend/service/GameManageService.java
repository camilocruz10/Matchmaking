package com.atomiclab.socialgamerbackend.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.ExecutionException;
import com.atomiclab.socialgamerbackend.domain.model.Games;

@Service
public interface GameManageService {
    public boolean createGame(Games game);

    public boolean deleteGame(String name, String image) throws InterruptedException, ExecutionException;

    public boolean updateGame(String Oldname, Games newGame) throws InterruptedException, ExecutionException;

    public Games getGame(String name) throws InterruptedException, ExecutionException;
}
