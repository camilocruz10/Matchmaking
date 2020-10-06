package com.atomiclab.socialgamerbackend.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Person;
import com.atomiclab.socialgamerbackend.service.FriendService;

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
public class FriendController {
    @Autowired
    FriendService friendService;

    @GetMapping("/friendsrequests")
    public List<Person> getRequests(@RequestHeader("X-Firebase-Auth") String token) throws InterruptedException, ExecutionException {
        return friendService.getFriendRequests(token);
    }
    @GetMapping("/profile/isFriend/{id:.+}")
    public boolean isFriend(@RequestHeader("X-Firebase-Auth") String token, @PathVariable String id) throws InterruptedException, ExecutionException {
        return friendService.isFriend(token, id);
    }
    @GetMapping("/profile/isRequestSend/{id:.+}")
    public boolean isRequestSend(@RequestHeader("X-Firebase-Auth") String token, @PathVariable String id) throws InterruptedException, ExecutionException {
        return friendService.isRequestSend(token, id);
    }
    @PostMapping("/profile/add/{id:.+}")
    public boolean addFriend(@RequestHeader("X-Firebase-Auth") String token, @PathVariable String id) throws InterruptedException, ExecutionException {
        return friendService.sendFriendRequest(token, id);
    }
    @PostMapping("/profile/accept")
    public boolean acceptFriend(@RequestHeader("X-Firebase-Auth") String token, @RequestBody Person person) throws InterruptedException, ExecutionException {
        return friendService.addFriend(token, person.getPersona_id());
    }
    @DeleteMapping("/profile/delete/{id:.+}")
    public boolean deleteFriend(@RequestHeader("X-Firebase-Auth") String token, @PathVariable String id) throws InterruptedException, ExecutionException {
        return friendService.deleteFriend(token, id);
    }
    @GetMapping("/friends")
    public List<Person> getFriends(@RequestHeader("X-Firebase-Auth") String token)
            throws InterruptedException, ExecutionException {
        return friendService.getFriends(token);
    }
}
