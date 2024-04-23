package com.exam.surveytool.controllers;

import com.exam.surveytool.models.Question;
import com.exam.surveytool.models.Survey;
import com.exam.surveytool.services.QuestionService;
import com.exam.surveytool.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    // Skapa en ny fråga knuten till en undersökning
    @PostMapping
    public ResponseEntity<?> createQuestion(@RequestBody Question question, @RequestParam Long surveyId) {
        try {
            Survey survey = surveyService.getSurveyById(surveyId);
            question.setSurvey(survey);
            Question createdQuestion = questionService.createQuestion(question);
            return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Survey not found with id: " + surveyId);
        }
    }

    // Hämta alla frågor för en specifik undersökning
    @GetMapping("/survey/{surveyId}")
    public ResponseEntity<List<Question>> getAllQuestionsBySurveyId(@PathVariable Long surveyId) {
        List<Question> questions = questionService.findAllQuestionsBySurvey(surveyId);
        return ResponseEntity.ok(questions);
    }

    // Hämta alla frågor från alla undersökningar
    @GetMapping("/all")
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionService.findAllQuestions();
        return ResponseEntity.ok(questions);
    }


    // Hämta en specifik fråga med dess ID
    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        return questionService.findQuestionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Uppdatera en befintlig fråga
    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        Optional<Question> updatedQuestion = questionService.updateQuestion(id, question);
        return updatedQuestion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Ta bort en fråga
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        try {
            questionService.deleteQuestion(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
