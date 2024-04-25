package com.exam.surveytool.controllers;

import com.exam.surveytool.dtos.QuestionDTO;
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

    //http://localhost:8080/api/questions/1
    @PostMapping("/{surveyId}")
    public ResponseEntity<?> createQuestion(@RequestBody QuestionDTO questionDTO, @PathVariable Long surveyId) {
        try {
            Survey survey = surveyService.getSurveyById(surveyId);
            questionDTO.setSurveyId(surveyId);  // Sätt surveyId i DTO:n ifall det behövs
            Question createdQuestion = questionService.createQuestion(questionDTO);
            return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Survey not found with id: " + surveyId);
        }
    }

    @GetMapping("/survey/{surveyId}")
    public ResponseEntity<List<Question>> getAllQuestionsBySurveyId(@PathVariable Long surveyId) {
        List<Question> questions = questionService.findAllQuestionsBySurvey(surveyId);
        return ResponseEntity.ok(questions);
    }

    @GetMapping()
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionService.findAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        return questionService.findQuestionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        Question updatedQuestion = questionService.updateQuestion(id, question);
        if (updatedQuestion != null) {
            return ResponseEntity.ok(updatedQuestion);
        } else {
            return ResponseEntity.notFound().build();
        }
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
