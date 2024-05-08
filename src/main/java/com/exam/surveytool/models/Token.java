package com.exam.surveytool.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Token { //will be created automatically by our system itself

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token; //will be a method that will generate a token
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime validatedAt;

    @ManyToOne //one way reference to user (many tokens to one user)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
