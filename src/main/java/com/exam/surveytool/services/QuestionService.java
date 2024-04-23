package com.exam.surveytool.services;

import com.exam.surveytool.models.Question;
import com.exam.surveytool.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    @Transactional
    public Question createQuestion (Question question) {
        return questionRepository.save(question);
    }


    @Transactional
    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new NoSuchElementException("Question with id: " + id + " was not found");
        }
        questionRepository.deleteById(id);
    }

    @Transactional
    public Question updateQuestion(Long id, Question questionDetails) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Question with id: " + id + " was not found"));
        question.setType(questionDetails.getType());
        question.setText(questionDetails.getText());
        return questionRepository.save(question);
    }
}
