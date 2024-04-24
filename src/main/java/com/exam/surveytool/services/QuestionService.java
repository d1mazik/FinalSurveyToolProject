package com.exam.surveytool.services;

import com.exam.surveytool.dtos.QuestionDTO;
import com.exam.surveytool.enums.EQuestionType;
import com.exam.surveytool.models.Option;
import com.exam.surveytool.models.Question;
import com.exam.surveytool.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    @Transactional
    public Question createQuestion (QuestionDTO questionDTO) { //modifierade with DTO att hantera olika frågetyper
        Question question = new Question();
        question.setType(questionDTO.getType());
        question.setText(questionDTO.getType() == EQuestionType.TEXT ? questionDTO.getText() : null);

        if (questionDTO.getType() == EQuestionType.OPTIONS && questionDTO.getOptions() != null) {
            question.setOptions(questionDTO.getOptions().stream()
                    .map(text -> new Option(null,text, question))//
                    .collect(Collectors.toList()));
        }

        if (questionDTO.getType() == EQuestionType.SCALE) {
            question.setMinScale(questionDTO.getMinScale());
            question.setMaxScale(questionDTO.getMaxScale());
        }

        return questionRepository.save(question);
    }


    @Transactional
    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new NoSuchElementException("Question with id: " + id + " was not found");
        }
        questionRepository.deleteById(id);
    }

    public Question updateQuestion(Long id, Question questionDetails) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Question with id " + id + " not found"));

        // Uppdatera frågans attribut
        question.setType(questionDetails.getType());
        question.setText(questionDetails.getText());

        // Spara och returnera den uppdaterade frågan
        return questionRepository.save(question);
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
