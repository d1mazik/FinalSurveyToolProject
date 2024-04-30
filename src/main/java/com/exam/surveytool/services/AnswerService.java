package com.exam.surveytool.services;

import com.exam.surveytool.dtos.AnswerDTO;
import com.exam.surveytool.models.Answer;
import com.exam.surveytool.models.Option;
import com.exam.surveytool.models.Question;
import com.exam.surveytool.repositories.AnswerRepository;
import com.exam.surveytool.repositories.OptionRepository;
import com.exam.surveytool.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository, OptionRepository optionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
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
        answer.setSelectedOption(option);  // Tilldela option här

        return answerRepository.save(answer);
    }
    // Andra metoder ...
}

