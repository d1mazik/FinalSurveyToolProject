package com.exam.surveytool.controllers;

import com.exam.surveytool.models.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.exam.surveytool.services.SurveyService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    private List<Survey> surveys = new ArrayList<>();
    private final SurveyService surveyService;

    @Autowired
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }


    @GetMapping
    public ResponseEntity<List<Survey>> getAllSurveys() {
        List<Survey> surveys = surveyService.getAllSurveys();
        return new ResponseEntity<>(surveys, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Survey> getSurveyById(@PathVariable Long id) {
        Survey survey = surveyService.getSurveyById(id);
        return new ResponseEntity<>(survey, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Survey> createSurvey(@RequestBody Survey survey) {
        Survey createdSurvey = surveyService.createSurvey(survey);
        return new ResponseEntity<>(createdSurvey, HttpStatus.CREATED);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<String> deleteSurvey(@PathVariable Long id) {
            surveyService.deleteSurvey(id);
            return ResponseEntity.ok("Survey deleted successfully");
    }


}
