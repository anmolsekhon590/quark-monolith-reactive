package com.anmolsekhon.reactiveauthentication.controllers;

import com.anmolsekhon.reactiveauthentication.models.Chat;
import com.anmolsekhon.reactiveauthentication.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/chat")
public class ChatController {
    private final ChatService chatService;

    @GetMapping
    public Flux<Chat> getAllChats(@AuthenticationPrincipal String username) {
        return chatService.getAllChats(username);
    }

    @PostMapping
    public void send(@AuthenticationPrincipal String username,
                     @RequestBody Chat chat) {
        chatService.send(username, chat);
    }
}
