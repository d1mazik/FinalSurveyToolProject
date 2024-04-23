package com.exam.surveytool.controllers;

import com.exam.surveytool.models.Question;
import com.exam.surveytool.models.Survey;
import com.exam.surveytool.services.QuestionService;
import com.exam.surveytool.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final SurveyService surveyService;

    @Autowired
    public QuestionController(QuestionService questionService, SurveyService surveyService) {
        this.questionService = questionService;
        this.surveyService = surveyService;
    }

    @PostMapping
    public ResponseEntity<?> createQuestion(@RequestBody Question question, @RequestParam(required = false) Long surveyId) {
        if (surveyId == null) {
            return ResponseEntity.badRequest().body("Survey ID must be provided");
        }

        Survey survey;
        try {
            survey = surveyService.getSurveyById(surveyId);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Survey not found with id " + surveyId);
        }

        question.setSurvey(survey);
        Question createdQuestion = questionService.createQuestion(question);
        survey.getQuestions().add(createdQuestion);
        surveyService.updateSurvey(surveyId, survey);

        return ResponseEntity.ok(createdQuestion);
    }

}
