package com.anmolsekhon.reactiveauthentication.services;

import com.anmolsekhon.reactiveauthentication.models.Chat;
import com.anmolsekhon.reactiveauthentication.repositories.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final TextEncryptor textEncryptor;

    public void send(String username, Chat chat) {
        final String encryptedMessage = textEncryptor.encrypt(chat.getMessage());

        final Chat builtChat = Chat.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .sentTo(chat.getSentTo())
                .sentBy(username)
                .message(encryptedMessage)
                .build();

        //saving the chat to the database
        chatRepository.save(builtChat)
                .subscribe();
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

    public Flux<Chat> getLatestChatForUser(String username) {
        return chatRepository.findFirstBySentToOrderByCreatedAt(username);
    }
}
