package com.anmolsekhon.reactiveauthentication.repositories;

import com.anmolsekhon.reactiveauthentication.models.Chat;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {
}
