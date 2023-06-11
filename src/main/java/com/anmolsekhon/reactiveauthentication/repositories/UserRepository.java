package com.anmolsekhon.reactiveauthentication.repositories;

import com.anmolsekhon.reactiveauthentication.entities.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByUsername(String username);
    Mono<UserView> findFriendsByUsername(String username);

//    Projections
    interface UserView {
        List<String> getFriends();
    }
}
