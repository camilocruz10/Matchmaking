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
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.Query.Direction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Este servicio se encarga de manejar la lógica relacionada con los chats y sus mensajes.
 * @author Atomic Lab
 * @version 1.0
 */
@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    FirebaseCrud firebaseCrud;
    @Autowired
    FirebaseSecAuth firebaseSecAuth;
    /**
     * se añade a la base de datos el nuevo chat.
     * @param chat Objeto de tipo chat
     * @param token String con el token del usuario
     * @return boolean que retorna si el metodo se realizo con exito o no
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public boolean createChat(Chat chat, String token) throws InterruptedException, ExecutionException {
        chat = initializeChat(chat, "Â¡Ya puedes comenzar a chatear con tu amigo", token);
        return firebaseCrud.saveWithoutId("Chat", chat, "Mensajes", chat.getMensajes().get(0));
    }
    /**
     * se añade a la base de datos el nuevo chat que será grupal.
     * @param token String con el token del usuario
     * @return identificador del chat
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public String createChatSquads(String token) throws InterruptedException, ExecutionException {
        List<String> integrantesForChat = new ArrayList<>();
        String id = firebaseCrud.createVoidAndGetId("Chat");
        Chat chat = new Chat();
        chat = initializeChat(chat, "Â¡Ya puedes comenzar a chatear con tu Squad!", token);
        integrantesForChat.add(firebaseSecAuth.getEmail(token));
        chat.setIntegrantes(integrantesForChat);
        firebaseCrud.saveSubCollection("Chat", id, "Mensajes", chat.getMensajes().get(0));
        chat.setMensajes(null);
        firebaseCrud.save(id, "Chat", chat);
        return id;
    }
    /**
     * Inicializar un chat
     * @param chat Objeto de tipo chat
     * @param mensajeInicial Mensaje inicial de un chat
     * @param token String con el token del usuario
     * @return Objeto de tipo chat ya creado en la base de datos
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public Chat initializeChat(Chat chat, String mensajeInicial, String token)
            throws InterruptedException, ExecutionException {
        List<Mensaje> mensajeInicio = new ArrayList<>();
        Date fechaActual = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        chat.setUltimomsj(fechaActual);
        Mensaje p = new Mensaje();
        p.setMensaje(mensajeInicial);
        p.setFechayhora(fechaActual);
        p.setRemitente(firebaseSecAuth.getEmail(token));
        mensajeInicio.add(p);
        chat.setMensajes(mensajeInicio);
        return chat;
    }
    /**
     * se añade a la base de datos el nuevo mensaje a la sub-colección del chat.
     * @param msj Objeto de tipo mensaje
     * @param chat_id identificador del chat
     * @return boolean que retorna si el metodo se realizo con exito o no
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public boolean sendMessage(Mensaje msj, String chat_id) throws InterruptedException, ExecutionException {
        boolean b = firebaseCrud.saveSubCollection("Chat", chat_id, "Mensajes", msj);
        Chat updatedChat = firebaseCrud.getById("Chat", chat_id).toObject(Chat.class);
        updatedChat.setUltimomsj(msj.getFechayhora());
        boolean b2 = firebaseCrud.update(chat_id, "Chat", updatedChat);
        return b && b2;
    }
    /**
     * lista de chats donde este usuario es un integrante
     * @param token String con el token del usuario
     * @return lista de los chats de un usuario
     * @throws InterruptedException
     * @throws ExecutionException 
     */
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
            List<QueryDocumentSnapshot> listDoc = collection.document(document.getId()).collection("Mensajes")
                    .orderBy("fechayhora", Direction.DESCENDING).limit(1).get().get().getDocuments();
            if (listDoc.size() > 0) {
                DocumentSnapshot doc = listDoc.get(0);
                Mensaje msj = doc.toObject(Mensaje.class);
                msj.setId(doc.getId());
                mensajes.add(msj);
                chat.setMensajes(mensajes);
            }
            chats.add(chat);
        }
        return chats;
    }
    /**
     * Recibe el identificador de un chat, y retorna dicho chat.
     * @param idChat identificador del chat
     * @return Objeto de tipo chat
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public Chat getChatById(String idChat) throws InterruptedException, ExecutionException {
        List<Mensaje> mensajes = new ArrayList<>();
        Chat chat = new Chat();
        DocumentSnapshot document = firebaseCrud.getById("Chat", idChat);
        if (document != null) {
            chat = document.toObject(Chat.class);
            chat.setId(idChat);
            for (DocumentSnapshot doc : firebaseCrud.getCollection("Chat").document(idChat).collection("Mensajes")
                    .orderBy("fechayhora", Direction.ASCENDING).get().get().getDocuments()) {
                Mensaje msj = doc.toObject(Mensaje.class);
                msj.setId(doc.getId());
                mensajes.add(msj);
            }
            chat.setMensajes(mensajes);
        }
        return chat;
    }
    /**
     * actualiza los campos del chat que hayan cambiado
     * @param idChat identificador del chat
     * @param chat objeto de tipo chat
     * @return boolean que retorna si el metodo se realizo exitosamente
     */
    public boolean updateChat(String idChat, Chat chat) {
        return firebaseCrud.update(idChat, "Chat", chat);
    }
}
