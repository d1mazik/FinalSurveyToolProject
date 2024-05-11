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
import java.util.stream.Collectors;

@Service
public class SurveyResponseSessionService {
    private SurveyRepository surveyRepository;
    private UserRepository userRepository;
    private AnswerRepository answerRepository;
    private QuestionRepository questionRepository;
    private SurveyResponseSessionRepository sessionRepository;
    private OptionRepository optionRepository;

    @Autowired
    public SurveyResponseSessionService(SurveyRepository surveyRepository
            , UserRepository userRepository
            , AnswerRepository answerRepository
            , QuestionRepository questionRepository
            , SurveyResponseSessionRepository sessionRepository
            , OptionRepository optionRepository) {
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
        session.setSurvey(survey);
        session.setUser(user);

        return sessionRepository.save(session);
    }

    @Transactional
    public void addAnswersToSession(Long sessionId, List<AnswerDTO> answerDTOs) {
        SurveyResponseSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        answerDTOs.forEach(dto -> {
            Answer answer = createAnswerFromDTO(dto, session);
            answerRepository.save(answer);
        });
    }

    private Answer createAnswerFromDTO(AnswerDTO dto, SurveyResponseSession session) {
        Question question = questionRepository.findById(dto.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + dto.getQuestionId()));
        Answer answer = new Answer();
        answer.setSession(session);
        answer.setQuestion(question);
        if (dto.getText() != null) {
            answer.setText(dto.getText());
        }
        if (dto.getSelectedScale() != null) {
            answer.setSelectedScale(dto.getSelectedScale());
        }
        if (dto.getSelectedOption() != null) {
            Option option = optionRepository.findById(dto.getSelectedOption())
                    .orElseThrow(() -> new RuntimeException("Option not found with id: " + dto.getSelectedOption()));
            answer.setSelectedOption(option);
        }
        return answer;
    }


    public void endSession(Long sessionId) {
        SurveyResponseSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        session.setEndedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }

    public SurveyResponseSession getSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
    }

    public List<AnswerDTO> getAnswersForSession(Long sessionId) {
        List<Answer> answers = answerRepository.findBySessionId(sessionId);
        return answers.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public AnswerDTO convertToDTO(Answer answer) {
        AnswerDTO dto = new AnswerDTO();
        dto.setId(answer.getId());
        dto.setQuestionId(answer.getQuestion() != null ? answer.getQuestion().getId() : null);
        dto.setSessionId(answer.getSession() != null ? answer.getSession().getId() : null);
        dto.setText(answer.getText());
        dto.setSelectedOption(answer.getSelectedOption() != null ? answer.getSelectedOption().getId() : null);
        dto.setSelectedScale(answer.getSelectedScale());
        return dto;
    }

}