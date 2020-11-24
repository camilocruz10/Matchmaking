package com.atomiclab.socialgamerbackend.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Clan;
import com.atomiclab.socialgamerbackend.domain.model.Person;
import com.atomiclab.socialgamerbackend.domain.model.Post;

import org.springframework.stereotype.Service;

@Service
public interface ClanService {
    public boolean createClan(Clan clan, String token) throws InterruptedException, ExecutionException;
    public List<Person> getMembers(String nombre_clan) throws InterruptedException, ExecutionException;
    public List<Post> getPosts(String nombre_clan, String token) throws InterruptedException, ExecutionException;
    public boolean addMember(Person person, String nombre_clan, String token) throws InterruptedException, ExecutionException;
    public boolean addPost(Post post, String nombre_clan, String token) throws InterruptedException, ExecutionException;
    public List<Clan> getClans() throws InterruptedException, ExecutionException;
    public List<Person> getRequests(String nombre_clan, String token) throws InterruptedException, ExecutionException;
    public boolean rejectRequest(String person_id, String nombre_clan, String token) throws InterruptedException, ExecutionException;
    public void deleteMember(String person_id, String nombre_clan, String token) throws InterruptedException, ExecutionException;
	public boolean isMember(String token, String nombre_clan) throws InterruptedException, ExecutionException;
	public boolean isRequestSend(String token, String nombre_clan) throws InterruptedException, ExecutionException;
	public boolean isAdmin(String token, String nombre_clan) throws InterruptedException, ExecutionException;
	public List<Clan> getMyClans(String token) throws InterruptedException, ExecutionException;
	public boolean addRequests(String nombre_clan, String token) throws InterruptedException, ExecutionException;
}
