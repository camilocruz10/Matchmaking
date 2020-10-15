package com.atomiclab.socialgamerbackend.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Chat;
import com.atomiclab.socialgamerbackend.domain.model.Mensaje;
import com.atomiclab.socialgamerbackend.repository.FirebaseCrud;
import com.atomiclab.socialgamerbackend.repository.FirebaseSecAuth;
import com.atomiclab.socialgamerbackend.service.ChatService;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.Query.Direction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    FirebaseCrud firebaseCrud;
    @Autowired
    FirebaseSecAuth firebaseSecAuth;

    public boolean createChat(Chat chat, String token) throws InterruptedException, ExecutionException {
        Date fechaActual = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()); 
        chat.setUltimomsj(fechaActual);
        System.out.println("Modifico Fecha" + chat.getId());
        Mensaje p = new Mensaje();
        p.setMensaje("¡Ya puedes comenzar a chatear con tu amigo!");
        p.setFechayhora(fechaActual);
        p.setRemitente(firebaseSecAuth.getEmail(token));
        return firebaseCrud.saveWithoutId("Chat", chat, "Mensajes", p);
    }

    public boolean sendMessage(Mensaje msj, String chat_id) throws InterruptedException, ExecutionException {
        boolean b = firebaseCrud.saveSubCollection("Chat", chat_id, "Mensajes", msj);
        Chat updatedChat = firebaseCrud.getById("Chat", chat_id).toObject(Chat.class);
        updatedChat.setUltimomsj(msj.getFechayhora());
        boolean b2 = firebaseCrud.update(chat_id, "Chat", updatedChat);
        return b && b2;
    }

    public List<Chat> getChats(String token) throws InterruptedException, ExecutionException {
        List<Chat> chats = new ArrayList<>();
        CollectionReference collection = firebaseCrud.getCollection("Chat");
        Query requestsQuery = collection.orderBy("ultimomsj", Direction.DESCENDING).whereArrayContains("integrantes",
                firebaseSecAuth.getEmail(token));
        for (DocumentSnapshot document : requestsQuery.get().get().getDocuments()) {
            List<Mensaje> mensajes = new ArrayList<>();
            Chat chat = new Chat();
            chat = document.toObject(Chat.class);
            chat.setId(document.getId());
            DocumentSnapshot doc = collection.document(document.getId()).collection("Mensajes")
                    .orderBy("fechayhora", Direction.DESCENDING).limit(1).get().get().getDocuments().get(0);
            Mensaje msj = doc.toObject(Mensaje.class);
            msj.setId(doc.getId());
            mensajes.add(msj);
            chat.setMensajes(mensajes);
            chats.add(chat);
        }
        return chats;
    }

    public Chat getChatById(String idChat) throws InterruptedException, ExecutionException {
        List<Mensaje> mensajes = new ArrayList<>();
        Chat chat = new Chat();
        DocumentSnapshot document = firebaseCrud.getById("Chat", idChat);
        chat = document.toObject(Chat.class);
        chat.setId(idChat);
        for (DocumentSnapshot doc : firebaseCrud.getCollection("Chat").document(idChat).collection("Mensajes")
                .orderBy("fechayhora", Direction.ASCENDING).get().get().getDocuments()) {
            Mensaje msj = doc.toObject(Mensaje.class);
            msj.setId(doc.getId());
            mensajes.add(msj);
        }
        chat.setMensajes(mensajes);
        return chat;
    }
}
