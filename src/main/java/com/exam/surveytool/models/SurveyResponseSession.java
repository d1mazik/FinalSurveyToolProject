package com.exam.surveytool.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Survey survey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Answer> answers;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "isActive")
    @Getter @Setter
    private Boolean isActive;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @PrePersist
    protected void onStart() {
        this.startedAt = LocalDateTime.now();
    }

    public void endSession() {
        this.endedAt = LocalDateTime.now();
    }
}
