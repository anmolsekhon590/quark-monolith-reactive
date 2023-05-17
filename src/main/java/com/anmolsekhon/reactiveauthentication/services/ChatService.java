package com.anmolsekhon.reactiveauthentication.services;

import com.anmolsekhon.reactiveauthentication.models.Chat;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    public void send(Chat chat) {
        System.out.println(chat);
    }
}
