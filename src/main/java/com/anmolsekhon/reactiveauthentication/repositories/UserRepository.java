package com.anmolsekhon.reactiveauthentication.repositories;

import com.anmolsekhon.reactiveauthentication.entities.User;
import com.anmolsekhon.reactiveauthentication.repositories.projections.UserView;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByUsername(String username);
    Mono<UserView> findFriendsByUsername(String username);
}
