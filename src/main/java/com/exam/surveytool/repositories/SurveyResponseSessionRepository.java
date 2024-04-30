package com.exam.surveytool.repositories;

import com.exam.surveytool.models.SurveyResponseSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyResponseSessionRepository extends JpaRepository<SurveyResponseSession, Long> {
        // Här kan du lägga till ytterligare metoder om du behöver, exempelvis för att hitta sessions för en specifik användare eller enkät.
    }
