package com.atomiclab.socialgamerbackend.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Clan;
import com.atomiclab.socialgamerbackend.domain.model.Person;
import com.atomiclab.socialgamerbackend.domain.model.Post;
import com.atomiclab.socialgamerbackend.service.ClanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/play")
public class ClanController {
    @Autowired
    ClanService clanService;

    @PostMapping("/clan/create")
    public boolean createClan(@RequestBody Clan clan, @RequestHeader("X-Firebase-Auth") String token)
            throws InterruptedException, ExecutionException {
        return clanService.createClan(clan, token);
    }
    @GetMapping("/clan/posts/{id:.+}") //--Ese id es el nombre del clan--
    public List<Post> getPosts(@PathVariable String id, @RequestHeader("X-Firebase-Auth") String token) 
            throws InterruptedException, ExecutionException {
        return clanService.getPosts(id, token);
    }
    @GetMapping("/clan/persons/{id:.+}") //--Ese id es el nombre del clan--
    public List<Person> getMembers(@PathVariable String id) 
            throws InterruptedException, ExecutionException {
        return clanService.getMembers(id);
    }
    @PostMapping("/clan/add/person") //--Aceptar miembro
    public boolean addMember(@RequestBody Person person, @RequestHeader("nombre_clan") String nombre_clan, @RequestHeader("X-Firebase-Auth") String token) 
            throws InterruptedException, ExecutionException {
        return clanService.addMember(person, nombre_clan, token);
    }
    @PostMapping("/clan/add/post")
    public boolean addPost(@RequestBody Post post, @RequestHeader("nombre_clan") String nombre_clan, @RequestHeader("X-Firebase-Auth") String token) 
            throws InterruptedException, ExecutionException {
        return clanService.addPost(post, nombre_clan, token);
    }
    @GetMapping("/clan/all") 
    public List<Clan> getClans() 
            throws InterruptedException, ExecutionException {
        return clanService.getClans();
    }
    @GetMapping("/clans") 
    public List<Clan> getMyClans(@RequestHeader("X-Firebase-Auth") String token) 
            throws InterruptedException, ExecutionException {
        return clanService.getMyClans(token);
    }
    @GetMapping("/clan/requests/{id:.+}") //--Ese id es el nombre del clan--
    public List<Person> getRequests(@PathVariable String id, @RequestHeader("X-Firebase-Auth") String token) 
            throws InterruptedException, ExecutionException {
        return clanService.getRequests(id, token);
    }
    @PostMapping("/clan/request/{id:.+}") //--Ese id es el nombre del clan--
    public boolean addRequest(@PathVariable String id, @RequestHeader("X-Firebase-Auth") String token) 
            throws InterruptedException, ExecutionException {
        return clanService.addRequests(id, token);
    }
    @GetMapping("/clan/isMember/{id:.+}")//--Ese id es el nombre del clan--
    public boolean isMember(@RequestHeader("X-Firebase-Auth") String token, @PathVariable String id) throws InterruptedException, ExecutionException {
        return clanService.isMember(token, id);
    }
    @GetMapping("/clan/isAdmin/{id:.+}")//--Ese id es el nombre del clan--
    public boolean isAdmin(@RequestHeader("X-Firebase-Auth") String token, @PathVariable String id) throws InterruptedException, ExecutionException {
        return clanService.isAdmin(token, id);
    }
    @GetMapping("/clan/isRequestSend/{id:.+}")//--Ese id es el nombre del clan--
    public boolean isRequestSend(@RequestHeader("X-Firebase-Auth") String token, @PathVariable String id) throws InterruptedException, ExecutionException {
        return clanService.isRequestSend(token, id);
    }
    @DeleteMapping("/clan/delete/{id:.+}") //--Este id es el persona_id osea el correo--
    public void deleteMember(@RequestHeader("nombre_clan") String nombre_clan, @PathVariable String id, @RequestHeader("X-Firebase-Auth") String token) throws InterruptedException, ExecutionException {
        clanService.deleteMember(id, nombre_clan, token);
    }
    @DeleteMapping("/clan/deleterequest/{id:.+}")  //--Este id es el persona_id osea el correo--
    public void deleteRequest(@RequestHeader("nombre_clan") String nombre_clan, @PathVariable String id, @RequestHeader("X-Firebase-Auth") String token) throws InterruptedException, ExecutionException {
        clanService.rejectRequest(id, nombre_clan, token);
    }
}
