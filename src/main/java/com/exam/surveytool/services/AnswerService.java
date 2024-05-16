package com.exam.surveytool.services;

import com.exam.surveytool.dtos.AnswerDTO;
import com.exam.surveytool.models.Answer;
import com.exam.surveytool.models.Option;
import com.exam.surveytool.models.Question;
import com.exam.surveytool.repositories.AnswerRepository;
import com.exam.surveytool.repositories.OptionRepository;
import com.exam.surveytool.repositories.QuestionRepository;
import com.exam.surveytool.repositories.SurveyResponseSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final SurveyResponseSessionRepository surveyResponseSessionRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository, OptionRepository optionRepository, SurveyResponseSessionRepository surveyResponseSessionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
        this.surveyResponseSessionRepository = surveyResponseSessionRepository;
    }

    public Answer createAnswer(AnswerDTO answerDTO) {
        Question question = questionRepository.findById(answerDTO.getQuestionId())
                .orElseThrow(() -> new NoSuchElementException("Question not found with id: " + answerDTO.getQuestionId()));
        Option option = null;
        if (answerDTO.getSelectedOption() != null) {
            option = optionRepository.findById(answerDTO.getSelectedOption())
                    .orElseThrow(() -> new NoSuchElementException("Option not found with id: " + answerDTO.getSelectedOption()));
        }

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setText(answerDTO.getText());
        answer.setScale(answerDTO.getScale());
        answer.setSelectedOption(option);
        answer.setSession(surveyResponseSessionRepository.findById(answerDTO.getSessionId())
                .orElseThrow(() -> new NoSuchElementException("Session not found with id: " + answerDTO.getSessionId())));

        return answerRepository.save(answer);
    }

    @Transactional
    public void deleteAnswer(Long id) {
        if (!answerRepository.existsById(id)) {
            throw new NoSuchElementException("Answer with id: " + id + " was not found");
        }
        answerRepository.deleteById(id);
    }

    @Transactional
    public Answer updateAnswer(Long id, AnswerDTO answerDTO) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Answer with id " + id + " not found"));

        if (answerDTO.getText() != null) {  //uppdateras den relevanta typen av frågor
            answer.setText(answerDTO.getText());
        }
        if (answerDTO.getSelectedOption() != null) {
            Option option = optionRepository.findById(answerDTO.getSelectedOption())
                    .orElseThrow(() -> new NoSuchElementException("Option not found with id: " + answerDTO.getSelectedOption()));
            answer.setSelectedOption(option);
        }
        if (answerDTO.getScale() != null) {
            answer.setScale(answerDTO.getScale());
        }

        return answerRepository.save(answer);
    }
}

