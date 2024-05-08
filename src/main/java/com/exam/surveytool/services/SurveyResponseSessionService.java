package com.exam.surveytool.services;
import com.exam.surveytool.dtos.AnswerDTO;
import com.exam.surveytool.dtos.SurveyResponseSessionDTO;
import com.exam.surveytool.models.*;
import com.exam.surveytool.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyResponseSessionService {
    private SurveyRepository surveyRepository;
    private UserRepository userRepository;
    private AnswerRepository answerRepository;
    private QuestionRepository questionRepository;
    private SurveyResponseSessionRepository sessionRepository;
    private OptionRepository optionRepository;

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

}
