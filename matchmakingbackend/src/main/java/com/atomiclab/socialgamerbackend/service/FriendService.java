package com.atomiclab.socialgamerbackend.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Person;

import org.springframework.stereotype.Service;

@Service
public interface FriendService {
    public List<Person> getFriendRequests(String token) throws InterruptedException, ExecutionException;
    public boolean isFriend(String token, String friendId) throws InterruptedException, ExecutionException;
    public boolean isRequestSend(String token, String friendId) throws InterruptedException, ExecutionException;
    public boolean addFriend(String token, String friendId) throws InterruptedException, ExecutionException;
    public boolean sendFriendRequest(String token, String friendId) throws InterruptedException, ExecutionException;
    public boolean deleteFriend(String token, String friendId) throws InterruptedException, ExecutionException;
    public List<Person> getFriends(String token, String id) throws InterruptedException, ExecutionException;
	public void rejectFriend(String token, String id) throws InterruptedException, ExecutionException;
}
