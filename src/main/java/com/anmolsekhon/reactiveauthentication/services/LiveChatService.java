package com.anmolsekhon.reactiveauthentication.services;

import com.anmolsekhon.reactiveauthentication.entities.Chat;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.*;

@Service
public class LiveChatService {
    // TODO: Replace this with redis or Kafka
    private final Map<String, List<Chat>> chatMap = new HashMap<>();

    public List<Chat> getAllLiveChats(String username) {
        List<Chat> liveChats = new ArrayList<>();
        if (chatMap.get(username) != null && !chatMap.get(username).isEmpty()) {
            liveChats = Collections.unmodifiableList(chatMap.get(username));
            chatMap.remove(username);
        }
        return liveChats;
    }

    public void addLiveChatToChatMap(Chat chat) {
        if (chatMap.containsKey(chat.getSentTo())) {
            chatMap.get(chat.getSentTo()).add(chat);
        } else {
            chatMap.put(chat.getSentTo(), new ArrayList<>(){{ add(chat); }});
        }
    }

    public Flux<ServerSentEvent<List<Chat>>> sseChatFluxForUser(String username) {
        return Flux.interval(Duration.ofSeconds(1)).map(sequence -> ServerSentEvent.<List<Chat>>builder()
                .id(String.valueOf(sequence)).event("all-chats-event").data(getAllLiveChats(username)).build());
    }
}
