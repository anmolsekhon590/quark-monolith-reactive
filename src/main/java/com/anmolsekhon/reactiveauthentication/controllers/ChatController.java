package com.anmolsekhon.reactiveauthentication.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/")
public class ChatController {
    @GetMapping
    public void send(@AuthenticationPrincipal String username) {
        System.out.println("");
    }
}
