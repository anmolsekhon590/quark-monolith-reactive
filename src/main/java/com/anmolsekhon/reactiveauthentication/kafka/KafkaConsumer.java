package com.anmolsekhon.reactiveauthentication.kafka;

import com.anmolsekhon.reactiveauthentication.models.Chat;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "chat", groupId = "group_01", containerFactory = "chatKafkaListenerContainerFactory")
    public void consume(Chat chat) {
        System.out.println(chat);
    }
}
