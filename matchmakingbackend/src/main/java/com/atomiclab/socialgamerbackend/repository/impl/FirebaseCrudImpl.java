package com.atomiclab.socialgamerbackend.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.repository.FirebaseCrud;
import com.atomiclab.socialgamerbackend.service.FirebaseService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirebaseCrudImpl implements FirebaseCrud {
    @Autowired
    FirebaseService firebaseService;

    @Override
    public boolean save(String id, String collectionName, Object data) {
        CollectionReference userCollection = firebaseService.getFirestore().collection(collectionName);
        ApiFuture<WriteResult> writeInSnapshot = userCollection.document(id).create(data);
        try {
            writeInSnapshot.get();
        } catch (InterruptedException e) {
            System.out.println("Interrupt");
        } catch (ExecutionException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean save(String collectionName, Object data) {
        CollectionReference userCollection = firebaseService.getFirestore().collection(collectionName);
        ApiFuture<WriteResult> writeInSnapshot = userCollection.document().create(data);
        try {
            writeInSnapshot.get();
        } catch (InterruptedException e) {
            System.out.println("Interrupt");
        } catch (ExecutionException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(String id, String collectionName, Object data) {
        CollectionReference userCollection = firebaseService.getFirestore().collection(collectionName);
        ApiFuture<WriteResult> writeInSnapshot = userCollection.document(id).set(data);
        try {
            writeInSnapshot.get();
        } catch (InterruptedException e) {
            System.out.println("Interrupt");
        } catch (ExecutionException e) {
            return false;
        }
        return true;
    }

    @Override
    public CollectionReference getCollection(String collectionName) {
        return firebaseService.getFirestore().collection(collectionName);
    }

    @Override
    public CollectionReference getSubCollection(String collectionName, String docId, String subcollectionName) {
        return firebaseService.getFirestore().collection(collectionName).document(docId).collection(subcollectionName);
    }

    @Override
    public boolean saveSubCollection(String collectionName, String docId, String subcollectionName, Object data) {
        CollectionReference subCollection = firebaseService.getFirestore().collection(collectionName).document(docId)
                .collection(subcollectionName);
        ApiFuture<WriteResult> writeInSnapshot = subCollection.document().create(data);
        try {
            writeInSnapshot.get();
        } catch (InterruptedException e) {
            System.out.println("Interrupt");
        } catch (ExecutionException e) {
            return false;
        }
        return true;
    }

    @Override
    public DocumentSnapshot getById(String collectionName, String id) throws InterruptedException, ExecutionException {
        CollectionReference dataCollection = firebaseService.getFirestore().collection(collectionName);
        ApiFuture<DocumentSnapshot> documentReference = dataCollection.document(id).get();
        return documentReference.get();
    }

    @Override
    public List<DocumentSnapshot> get(String collectionName) throws InterruptedException, ExecutionException {
        List<DocumentSnapshot> data = new ArrayList<DocumentSnapshot>();
        CollectionReference dataCollection = firebaseService.getFirestore().collection(collectionName);
        ApiFuture<QuerySnapshot> querySnapshot = dataCollection.get();
        for (DocumentSnapshot doc : querySnapshot.get().getDocuments()) {
            data.add(doc);
        }
        return data;
    }

    @Override
    public boolean delete(String id, String collectionName) {
        CollectionReference userCollection = firebaseService.getFirestore().collection(collectionName);
        ApiFuture<WriteResult> writeInSnapshot = userCollection.document(id).delete();
        try {
            writeInSnapshot.get();
        } catch (InterruptedException e) {
            System.out.println("Interrupt");
        } catch (ExecutionException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean saveWithoutId(String collectionName, Object collectionData, String subCollectionName, Object subCollectionData) {
        boolean funciono2 = false;
        System.out.println("-------------------antes");
        String id = firebaseService.getFirestore().collection(collectionName).document().getId(); 
        boolean funciono = update(id, collectionName, collectionData);
        System.out.println("-------------------despues");
        if(funciono){
            CollectionReference ppp = firebaseService.getFirestore().collection(collectionName).document(id).collection(subCollectionName);
            ppp.add(subCollectionData);
            //funciono2 = saveSubCollection(collectionName, id, subCollectionName, subCollectionData);
            System.out.println("-------------------despues22222222");
        } 
        return funciono && funciono2;
    }

}
