package com.atomiclab.socialgamerbackend.service.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.User;
import com.atomiclab.socialgamerbackend.repository.FirebaseCrud;
import com.atomiclab.socialgamerbackend.service.SearchService;
import com.google.cloud.firestore.DocumentSnapshot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    FirebaseCrud firebaseCrud;

    public Set<User> search(String collectionName, String searchWord, String fieldName)
            throws InterruptedException, ExecutionException {
        Set<User> personasResult = new HashSet<>();
        for (DocumentSnapshot doc : firebaseCrud.getCollection(collectionName).get().get().getDocuments()) {
            User userAux = doc.toObject(User.class);
            if (userAux.getNombre_usuario().toLowerCase().contains(searchWord.toLowerCase().trim())) {
                personasResult.add(userAux);
            }
        }
        return personasResult;
    }
}