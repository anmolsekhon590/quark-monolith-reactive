package com.anmolsekhon.reactiveauthentication.controllers;

import com.anmolsekhon.reactiveauthentication.models.Chat;
import com.anmolsekhon.reactiveauthentication.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/chat")
public class ChatController {
    private final ChatService chatService;

    @GetMapping
    public Flux<Chat> getAllChats(@AuthenticationPrincipal String username) {
        return chatService.getAllChats(username);
    }

    @Deprecated
    @GetMapping(value = "/latest", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> getLatestChatForUser(@AuthenticationPrincipal String username) {
        return chatService.getLatestChatForUser(username)
                .flatMap(chat -> Flux
                        .zip(Flux.interval(Duration.ofSeconds(2)),
                                Flux.fromStream(Stream.generate(() -> chat))
                        ).map(Tuple2::getT2)
                );
    }

    @PostMapping
    public Mono<Chat> send(@AuthenticationPrincipal String username,
                           @RequestBody Chat chat) {
            return chatService.send(username, chat);
    }
}
