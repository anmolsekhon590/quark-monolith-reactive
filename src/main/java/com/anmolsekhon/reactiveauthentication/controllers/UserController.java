package com.anmolsekhon.reactiveauthentication.controllers;

import com.anmolsekhon.reactiveauthentication.entities.User;
import com.anmolsekhon.reactiveauthentication.models.Friend;
import com.anmolsekhon.reactiveauthentication.models.auth.AuthRequest;
import com.anmolsekhon.reactiveauthentication.models.auth.AuthResponse;
import com.anmolsekhon.reactiveauthentication.security.PBKDF2Encoder;
import com.anmolsekhon.reactiveauthentication.services.UserService;
import com.anmolsekhon.reactiveauthentication.utility.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@AllArgsConstructor
public class UserController {
    private JWTUtil jwtUtil;
    private PBKDF2Encoder passwordEncoder;
    private UserService userService;

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest authRequest) {
        return userService.findByUsername(authRequest.getUsername())
                .filter(userDetails -> passwordEncoder.encode(authRequest.getPassword()).equals(userDetails.getPassword()))
                .map(userDetails -> ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(userDetails))))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    @PostMapping("/register")
    public Mono<User> register(@RequestBody AuthRequest authRequest) {
        return userService.register(authRequest);
    }

    @PostMapping("/friend")
    public Mono<User> addFriend(@RequestBody Friend friend) {
        return userService.addFriend(friend);
    }

    @PostMapping("/friend" + "/accept")
    public Mono<User> acceptFriendRequest(@AuthenticationPrincipal String username, @RequestBody Friend friend) {
        return userService.acceptFriendRequest(username, friend);
    }

    @PostMapping("/friend" + "/reject")
    public Mono<User> rejectFriendRequest(@AuthenticationPrincipal String username, @RequestBody Friend friend) {
        return userService.rejectFriendRequest(username, friend);
    }

    @GetMapping("/friend")
    public Mono<?> getAllFriends(@AuthenticationPrincipal String username) {
        return userService.getAllFriends(username);
    }
}
