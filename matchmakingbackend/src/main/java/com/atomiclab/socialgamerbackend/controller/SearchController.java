package com.atomiclab.socialgamerbackend.controller;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.User;
import com.atomiclab.socialgamerbackend.service.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/play")
public class SearchController {
    @Autowired
    SearchService searchService;

    @GetMapping("/search/{searchWord}")
    public Set<User> searchUser(@PathVariable String searchWord) throws InterruptedException, ExecutionException {
        Set<User> personasResult = new HashSet<>();
        personasResult = searchService.search("Persona", searchWord, "nombre_usuario");
        return personasResult;
    }

}
