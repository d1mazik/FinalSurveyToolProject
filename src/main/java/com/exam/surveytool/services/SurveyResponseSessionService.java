package com.exam.surveytool.services;

import com.exam.surveytool.dtos.SurveyResponseSessionDTO;
import com.exam.surveytool.models.Answer;
import com.exam.surveytool.models.Survey;
import com.exam.surveytool.models.SurveyResponseSession;
import com.exam.surveytool.models.User;
import com.exam.surveytool.repositories.AnswerRepository;
import com.exam.surveytool.repositories.SurveyRepository;
import com.exam.surveytool.repositories.SurveyResponseSessionRepository;
import com.exam.surveytool.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SurveyResponseSessionService {
    private SurveyRepository surveyRepository;
    private UserRepository userRepository;
    private AnswerRepository answerRepository;
    private SurveyResponseSessionRepository sessionRepository;

    @Transactional
    public SurveyResponseSession startSession(SurveyResponseSessionDTO sessionDTO) {
        Survey survey = surveyRepository.findById(sessionDTO.getSurveyId())
                .orElseThrow(() -> new RuntimeException("Survey not found"));
        User user = userRepository.findById(sessionDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        SurveyResponseSession session = new SurveyResponseSession();
        session.setSurvey(survey);
        session.setUser(user);

        return sessionRepository.save(session);
    }

    @Transactional
    public void addAnswersToSession(Long sessionId, Set<Long> answerIds) {
        SurveyResponseSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        Set<Answer> answers = answerIds.stream()
                .map(id -> answerRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Answer not found with id: " + id)))
                .collect(Collectors.toSet());

        session.getAnswers().addAll(answers);
        sessionRepository.save(session);
    }
}
