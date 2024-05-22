package com.exam.surveytool.services;

import com.exam.surveytool.dtos.EndSessionDTO;
import com.exam.surveytool.dtos.SurveyResponseSessionDTO;
import com.exam.surveytool.models.Survey;
import com.exam.surveytool.models.SurveyResponseSession;
import com.exam.surveytool.models.User;
import com.exam.surveytool.repositories.AnswerRepository;
import com.exam.surveytool.repositories.SurveyRepository;
import com.exam.surveytool.repositories.SurveyResponseSessionRepository;
import com.exam.surveytool.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SurveyResponseSessionService {
    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final SurveyResponseSessionRepository sessionRepository;

    @Transactional
    public SurveyResponseSession startSession(SurveyResponseSessionDTO sessionDTO) {
        Survey survey = surveyRepository.findById(sessionDTO.getSurveyId())
                .orElseThrow(() -> new RuntimeException("Survey not found"));
        User user = userRepository.findById(sessionDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        SurveyResponseSession session = new SurveyResponseSession();
        session.setSurvey(survey);
        session.setUser(user);
        session.setIsActive(true);

        return sessionRepository.save(session);
    }


    @Transactional
    public EndSessionDTO endSession(Long sessionId) {
        SurveyResponseSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + sessionId));

        session.setEndedAt(LocalDateTime.now());
        session.setIsActive(false);

        session = sessionRepository.save(session);
        return toEndSessionDTO(session);
    }

    private EndSessionDTO toEndSessionDTO(SurveyResponseSession session) {
        return new EndSessionDTO(
                session.getEndedAt(),
                session.getIsActive()
        );
    }

}
