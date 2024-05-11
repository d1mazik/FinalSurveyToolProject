package com.exam.surveytool.services;

import com.exam.surveytool.dtos.AnswerDTO;
import com.exam.surveytool.models.Answer;
import com.exam.surveytool.models.Option;
import com.exam.surveytool.models.Question;
import com.exam.surveytool.models.SurveyResponseSession;
import com.exam.surveytool.repositories.AnswerRepository;
import com.exam.surveytool.repositories.OptionRepository;
import com.exam.surveytool.repositories.QuestionRepository;
import com.exam.surveytool.repositories.SurveyResponseSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final SurveyResponseSessionRepository sessionRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository, OptionRepository optionRepository, SurveyResponseSessionRepository sessionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
        this.sessionRepository = sessionRepository;
    }

    public AnswerDTO createAnswer(AnswerDTO answerDTO) {
        Question question = questionRepository.findById(answerDTO.getQuestionId())
                .orElseThrow(() -> new NoSuchElementException("Question not found with id: " + answerDTO.getQuestionId()));
        SurveyResponseSession session = sessionRepository.findById(answerDTO.getSessionId())
                .orElseThrow(() -> new NoSuchElementException("Session not found with id: " + answerDTO.getSessionId()));
        Option option = null;
        if (answerDTO.getSelectedOption() != null) {
            option = optionRepository.findById(answerDTO.getSelectedOption())
                    .orElseThrow(() -> new NoSuchElementException("Option not found with id: " + answerDTO.getSelectedOption()));
        }

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setSession(session);
        answer.setText(answerDTO.getText());
        answer.setSelectedScale(answerDTO.getSelectedScale());
        answer.setSelectedOption(option);
        answer = answerRepository.save(answer);

        // Skapa DTO att returnera
        AnswerDTO responseDTO = new AnswerDTO();
        responseDTO.setQuestionId(answer.getQuestion().getId());
        responseDTO.setSessionId(answer.getSession().getId());
        responseDTO.setText(answer.getText());
        responseDTO.setSelectedOption(answer.getSelectedOption() != null ? answer.getSelectedOption().getId() : null);
        responseDTO.setSelectedScale(answer.getSelectedScale());
        return responseDTO;
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
        if (answerDTO.getSelectedScale() != null) {
            answer.setSelectedScale(answerDTO.getSelectedScale());
        }

        return answerRepository.save(answer);
    }

    public List<Answer> findAnswersBySessionId(Long sessionId) {
        return answerRepository.findBySessionId(sessionId);
    }

}