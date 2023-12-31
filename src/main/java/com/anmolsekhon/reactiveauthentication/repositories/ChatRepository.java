package com.anmolsekhon.reactiveauthentication.repositories;

import com.anmolsekhon.reactiveauthentication.entities.Chat;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {
    Flux<Chat> findAllBySentByOrSentTo(String sentBy, String sentTo);
    @Deprecated Flux<Chat> findFirstBySentToOrderByCreatedAtDesc(String sentTo);
}
