package com.exam.surveytool.services;

import com.exam.surveytool.models.Question;
import com.exam.surveytool.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public Optional<Question> updateQuestion(Long id, Question questionDetails) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            question.setType(questionDetails.getType());
            question.setText(questionDetails.getText());
            return Optional.of(questionRepository.save(question));
        }
        return Optional.empty();
    }

    public List<Question> findAllQuestionsBySurvey(Long surveyId) {
        return questionRepository.findBySurveyId(surveyId); // Använder repository-metoden för att hämta frågor
    }

    public List<Question> findAllQuestions() {
        return questionRepository.findAll();
    }

    public Optional<Question> findQuestionById(Long id) {
        return questionRepository.findById(id);
    }
}
