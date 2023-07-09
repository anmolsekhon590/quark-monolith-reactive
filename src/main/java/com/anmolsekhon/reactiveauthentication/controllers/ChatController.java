package com.anmolsekhon.reactiveauthentication.controllers;

import com.anmolsekhon.reactiveauthentication.entities.Chat;
import com.anmolsekhon.reactiveauthentication.services.ChatService;
import com.anmolsekhon.reactiveauthentication.services.LiveChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/chat")
public class ChatController {
    private final ChatService chatService;
    private final LiveChatService liveChatService;

    @GetMapping
    public Flux<Chat> getAllChats(@AuthenticationPrincipal String username) {
        return chatService.getAllChats(username);
    }

    @GetMapping("/latest")
    public Flux<ServerSentEvent<List<Chat>>> getLatestChatListForUser(@AuthenticationPrincipal String username) {
        return liveChatService.sseChatFluxForUser(username);
    }

    @PostMapping
    public Mono<Chat> send(@AuthenticationPrincipal String username,
                           @RequestBody Chat chat) {
            return chatService.send(username, chat);
    }
}
