package com.exam.surveytool.models;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Andra fält ...

    // ManyToOne-relation tillbaka till Survey
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;



    // Konstruktorer, getters och setters
}
