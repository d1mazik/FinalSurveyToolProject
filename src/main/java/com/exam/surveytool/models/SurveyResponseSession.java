package com.exam.surveytool.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "survey_response_session")
public class SurveyResponseSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id", nullable = false)
    @JsonBackReference
    private Survey survey; // Associerar sessionen med en specifik undersökning.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Associerar sessionen med en specifik användare.

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Answer> answers; // Mängd av svar associerade med sessionen.

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt; // Tiden då sessionen startade.

    @Column(name = "ended_at")
    private LocalDateTime endedAt; // Tiden då sessionen avslutades.

    @PrePersist
    protected void onStart() {
        this.startedAt = LocalDateTime.now(); // Initiera starttiden när sessionen skapas.
    }

    public void endSession() {
        this.endedAt = LocalDateTime.now(); // Sätt tiden för när sessionen avslutas.
    }
}
