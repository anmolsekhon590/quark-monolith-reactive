package com.anmolsekhon.reactiveauthentication.controllers;

import com.anmolsekhon.reactiveauthentication.models.Chat;
import com.anmolsekhon.reactiveauthentication.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public void send(@AuthenticationPrincipal String username,
                     @RequestBody Chat chat) {
        chatService.send(Chat.builder()
                .id(UUID.randomUUID().toString())
                .sentTo(chat.getSentTo())
                .message(chat.getMessage())
                .build());
    }
}
