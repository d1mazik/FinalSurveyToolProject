package com.exam.surveytool.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "users") // Se till att tabellnamnet matchar det exakta namnet i din databas
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<SurveyResponseSession> surveyResponseSessions;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role", // Tabellnamnet för join-tabellen
            joinColumns = @JoinColumn(name = "user_id"), // Namnet på kolumnen i join-tabellen som refererar till denna User entitet
            inverseJoinColumns = @JoinColumn(name = "role_id") // Namnet på den motsatta kolumnen som refererar till Role entiteten
    )
    private Set<Role> roles;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
