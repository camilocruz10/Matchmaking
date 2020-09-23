package com.atomiclab.socialgamerbackend.repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.DocumentSnapshot;

import org.springframework.stereotype.Service;

@Service
public interface FirebaseCrud{
    
    public boolean save(String id, String collectionName, Object data);
    public boolean update(String id, String collectionName, Object data);
    public DocumentSnapshot getById(String collectionName, String id) throws InterruptedException, ExecutionException;
    public List<DocumentSnapshot> get(String collectionName) throws InterruptedException, ExecutionException;
    public String delete(String id);
}
