package com.atomiclab.socialgamerbackend.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.ExecutionException;
import com.atomiclab.socialgamerbackend.domain.model.Chat;
import com.atomiclab.socialgamerbackend.domain.model.Mensaje;
import com.atomiclab.socialgamerbackend.domain.model.Person;

@Service
public interface ChatService {
    public boolean createChat(Chat chat, String token) throws InterruptedException, ExecutionException;

    public String createChatSquads(String token) throws InterruptedException, ExecutionException;

    public boolean sendMessage(Mensaje msj, String chat_id) throws InterruptedException, ExecutionException;

    public List<Chat> getChats(String token) throws InterruptedException, ExecutionException;

    public Chat getChatById(String idChat) throws InterruptedException, ExecutionException;

    public boolean updateChat(String idChat, Chat chat);
}
