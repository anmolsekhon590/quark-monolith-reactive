package com.anmolsekhon.reactiveauthentication.services;

import com.anmolsekhon.reactiveauthentication.models.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(Chat chat) {
        kafkaTemplate.send("chat", chat.getMessage());
    }
}
