package com.exam.surveytool.services;

import com.exam.surveytool.dtos.QuestionDTO;
import com.exam.surveytool.enums.EQuestionType;
import com.exam.surveytool.models.Option;
import com.exam.surveytool.models.Question;
import com.exam.surveytool.models.Survey;
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
    @Autowired
    public QuestionService(QuestionRepository questionRepository, SurveyService surveyService) {
        this.questionRepository = questionRepository;
        this.surveyService = surveyService;
    }

    @Transactional
    public Question createQuestion(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setType(questionDTO.getType());

        // Sätt text endast om typen är TEXT. Annars, ignorera textfältet.
        if (questionDTO.getType().equals(EQuestionType.TEXT)) {
            question.setText(questionDTO.getText());
        }

        // För OPTIONS, skapa och lägg till options i 'question'.
        if (questionDTO.getType().equals(EQuestionType.OPTIONS) && questionDTO.getOptions() != null) {
            for (String optionText : questionDTO.getOptions()) {
                Option option = new Option();
                option.setText(optionText);
                question.setText(questionDTO.getText());
                question.addOption(option);
            }
        }

        // För SCALE, sätt minScale och maxScale.
        if (questionDTO.getType().equals(EQuestionType.SCALE)) {
            question.setMinScale(questionDTO.getMinScale());
            question.setMaxScale(questionDTO.getMaxScale());
            question.setText(questionDTO.getText());
        }

        // Hämta Survey via SurveyService och associera med frågan
        Survey survey = surveyService.getSurveyById(questionDTO.getSurveyId());
        question.setSurvey(survey);

        // Spara frågan i databasen
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
