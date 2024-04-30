package com.exam.surveytool.services;

import com.exam.surveytool.dtos.AnswerDTO;
import com.exam.surveytool.models.Answer;
import com.exam.surveytool.models.Question;
import com.exam.surveytool.models.SurveyResponseSession;
import com.exam.surveytool.repositories.AnswerRepository;
import com.exam.surveytool.repositories.QuestionRepository;
import com.exam.surveytool.repositories.SurveyResponseSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final SurveyResponseSessionRepository surveyResponseSessionRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository, SurveyResponseSessionRepository surveyResponseSessionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.surveyResponseSessionRepository = surveyResponseSessionRepository;
    }

    public Answer createAnswer(AnswerDTO answerDTO) {
        Question question = questionRepository.findById(answerDTO.getQuestionId())
                .orElseThrow(() -> new NoSuchElementException("Question not found with id: " + answerDTO.getQuestionId()));
        SurveyResponseSession surveyResponseSession = surveyResponseSessionRepository.findById(answerDTO.getSurveyResponseSessionId())
                .orElseThrow(() -> new NoSuchElementException("SurveyResponseSession not found with id: " + answerDTO.getSurveyResponseSessionId()));

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setSurveyResponseSession(surveyResponseSession);
        answer.setText(answerDTO.getText());
        answer.setSelectedOption(answerDTO.getSelectedOption());
        answer.setScale(answerDTO.getScale());

        return answerRepository.save(answer);
    }

    // Andra metoder ...
}
