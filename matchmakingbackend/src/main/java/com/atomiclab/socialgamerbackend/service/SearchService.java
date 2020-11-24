package com.atomiclab.socialgamerbackend.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Clan;
import com.atomiclab.socialgamerbackend.domain.model.Games;
import com.atomiclab.socialgamerbackend.domain.model.Post;
import com.atomiclab.socialgamerbackend.domain.model.Squad;
import com.atomiclab.socialgamerbackend.domain.model.User;

import org.springframework.stereotype.Service;
/**
 * Lista de los servicios de SearchService
 * @author Atomic Lab
 * @version 1.0
 */
@Service
public interface SearchService {

    public List<User> searchUser(String collectionName, String searchWord) throws InterruptedException, ExecutionException;
    public List<Games> searchGames(String collectionName, String searchWord) throws InterruptedException, ExecutionException;
    public List<Post> searchPost(String collectionName, String searchWord) throws InterruptedException, ExecutionException;
    public List<Squad> searchSquad(String collectionName, String searchWord) throws InterruptedException, ExecutionException;
    public List<Clan> searchClan(String collectionName, String searchWord) throws InterruptedException, ExecutionException;
}
