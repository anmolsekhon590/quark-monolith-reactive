package com.anmolsekhon.reactiveauthentication.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "chats")
public class Chat {
    @Id
    private String id;
    private String message;
    private LocalDateTime createdAt;
    private String sentTo;
    private String sentBy;
}
