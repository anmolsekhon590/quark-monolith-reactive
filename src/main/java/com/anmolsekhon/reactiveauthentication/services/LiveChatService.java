package com.anmolsekhon.reactiveauthentication.services;

import com.anmolsekhon.reactiveauthentication.entities.Chat;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LiveChatService {
    private final Map<String, List<Chat>> chatMap = new HashMap<>();

    public List<Chat> getAllLiveChats(String username) {
        List<Chat> liveChats = chatMap.get(username);
        if (liveChats != null && !liveChats.isEmpty()) {
           liveChats.clear();
        }
        return liveChats;
    }

    public void addLiveChatToChatMap(String username, Chat chat) {
        if(chatMap.containsKey(username)) {
            chatMap.get(username).add(chat);
        } else {
            chatMap.put(username, new ArrayList<>(){{
                add(chat);
            }});
        }
    }

    public Flux<ServerSentEvent<List<Chat>>> sseChatFluxForUser(String username) {
        return Flux.interval(Duration.ofSeconds(1)).map(sequence -> ServerSentEvent.<List<Chat>>builder()
                .id(String.valueOf(sequence)).event("all-chats-event").data(getAllLiveChats(username)).build());
    }
}
