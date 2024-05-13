package com.exam.surveytool.services;
import com.exam.surveytool.dtos.AnswerDTO;
import com.exam.surveytool.dtos.SurveyResponseSessionDTO;
import com.exam.surveytool.models.*;
import com.exam.surveytool.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SurveyResponseSessionService {
    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final SurveyResponseSessionRepository sessionRepository;
    private final OptionRepository optionRepository;

    @Autowired
    public SurveyResponseSessionService(
            SurveyRepository surveyRepository,
            UserRepository userRepository,
            AnswerRepository answerRepository,
            QuestionRepository questionRepository,
            SurveyResponseSessionRepository sessionRepository,
            OptionRepository optionRepository) {
        this.surveyRepository = surveyRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.sessionRepository = sessionRepository;
        this.optionRepository = optionRepository;
    }

    @Transactional
    public SurveyResponseSession startSession(SurveyResponseSessionDTO sessionDTO) {
        Survey survey = surveyRepository.findById(sessionDTO.getSurveyId())
                .orElseThrow(() -> new RuntimeException("Survey not found"));
        User user = userRepository.findById(sessionDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        SurveyResponseSession session = new SurveyResponseSession();
        session.setSurvey(survey); //en ny session-objekt associeras med en given survey. dvs survey-objekt tilldelas till sessionen
        session.setUser(user);
        session.setStartedAt(LocalDateTime.now());
        session.setActive(true);//true innebär att sessionen är aktiv och pågår

        survey.getSessions().add(session);//om survey har flera session-objekter, då läggs den nyss skapade sessionen till denna lista
        return sessionRepository.save(session);
    }

    @Transactional
    public void addAnswersToSession(Long sessionId, List<AnswerDTO> answerDTOs) {
        SurveyResponseSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        for (AnswerDTO dto : answerDTOs) {
            Answer answer = new Answer();
            answer.setSession(session);  // Sätt sessionen här
            answer.setQuestion(questionRepository.findById(dto.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found with id: " + dto.getQuestionId())));
            if (dto.getText() != null) {
                answer.setText(dto.getText());
            }
            if (dto.getScale() != null) {
                answer.setScale(dto.getScale());
            }
            if (dto.getSelectedOption() != null) {
                Option option = optionRepository.findById(dto.getSelectedOption())
                        .orElseThrow(() -> new RuntimeException("Option not found with id: " + dto.getSelectedOption()));
                answer.setSelectedOption(option);
            }

            answerRepository.save(answer); // Spara svaret i databasen
        }
    }

    public void endSession(Long sessionId) {
        SurveyResponseSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + sessionId));

        session.setEndedAt(LocalDateTime.now());
        session.setActive(false);
        sessionRepository.save(session);
    }



}
