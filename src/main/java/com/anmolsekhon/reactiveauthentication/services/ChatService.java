package com.anmolsekhon.reactiveauthentication.services;

import com.anmolsekhon.reactiveauthentication.entities.Chat;
import com.anmolsekhon.reactiveauthentication.repositories.ChatRepository;
import com.anmolsekhon.reactiveauthentication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final TextEncryptor textEncryptor;
    private final LiveChatService liveChatService;

    public Mono<Chat> send(String username, Chat chat) {
        return userRepository.findByUsername(chat.getSentTo())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("User doesn't exist")))
                .hasElement().flatMap(user -> {
                    if (chat.getMessage().isEmpty())
                        return Mono.error(new IllegalArgumentException("Message cannot be empty"));

                    final String encryptedMessage = textEncryptor.encrypt(chat.getMessage());

                    final Chat builtChat = Chat.builder()
                            .id(UUID.randomUUID().toString())
                            .createdAt(LocalDateTime.now())
                            .sentTo(chat.getSentTo())
                            .sentBy(username)
                            .message(encryptedMessage)
                            .build();

                    liveChatService.addLiveChatToChatMap(Chat.builder()
                                    .id(builtChat.getId())
                                    .createdAt(builtChat.getCreatedAt())
                                    .sentTo(builtChat.getSentTo())
                                    .sentBy(builtChat.getSentBy())
                                    .message(chat.getMessage())
                            .build());
                    return chatRepository.save(builtChat);
                });
    }

    public Flux<Chat> getAllChats(String username) {
        return chatRepository.findAllBySentByOrSentTo(username, username)
                .map(chat -> Chat.builder()
                        .id(chat.getId())
                        .sentBy(chat.getSentBy())
                        .sentTo(chat.getSentTo())
                        .createdAt(chat.getCreatedAt())
                        .message(textEncryptor.decrypt(chat.getMessage()))
                        .build());
    }

    @Deprecated
    public Flux<Chat> getLatestChatForUser(String username) {
        return chatRepository.findFirstBySentToOrderByCreatedAtDesc(username);
    }
}
