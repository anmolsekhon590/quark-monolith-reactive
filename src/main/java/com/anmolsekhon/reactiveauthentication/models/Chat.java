package com.anmolsekhon.reactiveauthentication.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    private String id;
    private String message;
    private String sentTo;
}
