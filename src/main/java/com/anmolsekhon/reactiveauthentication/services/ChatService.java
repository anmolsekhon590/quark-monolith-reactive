package com.anmolsekhon.reactiveauthentication.services;

import com.anmolsekhon.reactiveauthentication.models.Chat;
import com.anmolsekhon.reactiveauthentication.repositories.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final KafkaTemplate<String, Chat> kafkaTemplate;
    private final ChatRepository chatRepository;

    public void send(Chat chat) {
        // sends the message over Kafka
        kafkaTemplate.send("chat", chat);

        //saving the chat to the database
        chatRepository.save(chat);
    }
}
