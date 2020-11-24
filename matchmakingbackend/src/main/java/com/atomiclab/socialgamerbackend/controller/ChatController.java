package com.atomiclab.socialgamerbackend.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Chat;
import com.atomiclab.socialgamerbackend.domain.model.Mensaje;
import com.atomiclab.socialgamerbackend.service.ChatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/play")
public class ChatController {

    @Autowired
    ChatService chatService;

    @PostMapping("/chat/create")
    public boolean createChat(@RequestBody Chat chat, @RequestHeader("X-Firebase-Auth") String token)
            throws InterruptedException, ExecutionException {
        return chatService.createChat(chat, token);
    }

    @PostMapping("/chat/message")
    public boolean sendMessage(@RequestBody Mensaje msj, @RequestHeader("Chat-id") String chat_id,
            @RequestHeader("X-Firebase-Auth") String token) throws InterruptedException, ExecutionException {
        msj.setFechayhora(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        msj.setId(null);
        return chatService.sendMessage(msj, chat_id);
    }

    @GetMapping("/chats")
    public List<Chat> getChats(@RequestHeader("X-Firebase-Auth") String token)
            throws InterruptedException, ExecutionException {
        return chatService.getChats(token);
    }

    @GetMapping("/chat/{idChat}")
    public Chat getChatById(@PathVariable String idChat) throws InterruptedException, ExecutionException {
        return chatService.getChatById(idChat);
    }

}
