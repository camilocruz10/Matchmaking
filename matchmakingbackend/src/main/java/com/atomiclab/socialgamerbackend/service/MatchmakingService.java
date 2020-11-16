package com.atomiclab.socialgamerbackend.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Matchmaking;


public interface MatchmakingService {
	void create(Matchmaking matchmaking, String token) throws InterruptedException, ExecutionException;
	List<Matchmaking> findMatch(Matchmaking matchmaking) throws InterruptedException, ExecutionException;
	void delete(String token) throws InterruptedException, ExecutionException; 
}
