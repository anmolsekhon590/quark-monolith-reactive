package com.anmolsekhon.reactiveauthentication.services;

import com.anmolsekhon.reactiveauthentication.models.Chat;
import com.anmolsekhon.reactiveauthentication.repositories.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final KafkaTemplate<String, Chat> kafkaTemplate;
    private final ChatRepository chatRepository;
    private final TextEncryptor textEncryptor;

    public void send(String username, Chat chat) {
        String encryptedMessage = textEncryptor.encrypt(chat.getMessage());

        final Chat builtChat = Chat.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .sentTo(chat.getSentTo())
                .sentBy(username)
                .message(encryptedMessage)
                .build();

        // sends the message over Kafka
        kafkaTemplate.send("chat", builtChat);

        //saving the chat to the database
        chatRepository.save(builtChat)
                .subscribe();
    }
}
