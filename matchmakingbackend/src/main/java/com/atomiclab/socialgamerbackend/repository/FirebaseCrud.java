package com.atomiclab.socialgamerbackend.repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;

import org.springframework.stereotype.Service;

@Service
public interface FirebaseCrud {
        public boolean save(String id, String collectionName, Object data);
        public boolean save(String collectionName, Object data);
        public boolean update(String id, String collectionName, Object data);
        public CollectionReference getCollection(String collectionName);
        public CollectionReference getSubCollection(String collectionName, String docId, String subcollectionName);
        public boolean saveSubCollection(String collectionName, String docId, String subcollectionName, Object data);
        public DocumentSnapshot getById(String collectionName, String id)throws InterruptedException, ExecutionException;
        public List<DocumentSnapshot> get(String collectionName) throws InterruptedException, ExecutionException;
        public boolean delete(String id, String collectionName);
        public boolean deleteInSubCollection(String id, String collectionName, String IdSubDocument, String subCollectionName);
        public boolean saveWithoutId(String collectionName, Object collectionData, String subCollectionName, Object subCollectionData);
        public String getIdWithUniqueField(String collectionUniqueValue, String collectionName) throws InterruptedException, ExecutionException;
        public String createVoidAndGetId(String collectionName);
        public Query collectionGroupSearch(String subCollection, String attributeName, String searchWord);
}
