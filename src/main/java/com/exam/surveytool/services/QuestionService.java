package com.exam.surveytool.services;

import com.exam.surveytool.dtos.QuestionDTO;
import com.exam.surveytool.enums.EQuestionType;
import com.exam.surveytool.models.Option;
import com.exam.surveytool.models.Question;
import com.exam.surveytool.models.Survey;
import com.exam.surveytool.repositories.OptionRepository;
import com.exam.surveytool.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final SurveyService surveyService;
    private final OptionRepository optionRepository;
    @Autowired
    public QuestionService(QuestionRepository questionRepository, SurveyService surveyService, OptionRepository optionRepository) {
        this.questionRepository = questionRepository;
        this.surveyService = surveyService;
        this.optionRepository = optionRepository;
    }

    @Transactional
    public Question createQuestion(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setType(questionDTO.getType());
        question.setText(questionDTO.getText()); // Detta antar att text alltid sätts, oavsett frågetyp

        Survey survey = surveyService.getSurveyById(questionDTO.getSurveyId());
        question.setSurvey(survey);

        if (questionDTO.getType() == EQuestionType.OPTIONS) {
            questionDTO.getOptions().forEach(optionText -> {
                Option option = new Option();
                option.setText(optionText);
                option.setQuestion(question); // Sätt frågan för varje option
                question.getOptions().add(option); // Lägg till option i frågans option-lista
            });
        } else if (questionDTO.getType() == EQuestionType.SCALE) {
            question.setMinScale(questionDTO.getMinScale());
            question.setMaxScale(questionDTO.getMaxScale());
        }

        // Eftersom CascadeType är ALL, kommer options att sparas när vi sparar frågan
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
        return questionRepository.findBySurveyId(surveyId);
    }

    public List<Question> findAllQuestions() {
        return questionRepository.findAll();
    }

    public Optional<Question> findQuestionById(Long id) {
        return questionRepository.findById(id);
    }
}
