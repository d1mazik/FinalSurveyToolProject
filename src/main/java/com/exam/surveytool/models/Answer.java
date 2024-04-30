package com.exam.surveytool.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text; // för TEXT-baserade svar

    // Anta att selectedOption refererar till ett ID från en lista av Options för frågan.
    // Det kan behövas en entitet eller en lista som håller i möjliga Options om de är begränsade.
    private Long selectedOption; // för val av en Option

    private Integer scale; // för SCALE-baserade svar

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_response_session_id", nullable = false)
    private SurveyResponseSession surveyResponseSession;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Överväg att lägga till getter och setter för Option entiteten om den existerar
}

