package com.exam.surveytool.controllers;

import com.exam.surveytool.dtos.AnswerDTO;
import com.exam.surveytool.models.Answer;
import com.exam.surveytool.services.AnswerService;
import com.exam.surveytool.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;

    @Autowired
    public AnswerController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }



    // Lägg till endpoints för POST, GET, PUT, och DELETE
    @PostMapping
    public ResponseEntity<Answer> createAnswer(@RequestBody AnswerDTO answerDTO) {
        Answer answer = answerService.createAnswer(answerDTO);
        return new ResponseEntity<>(answer, HttpStatus.CREATED);
    }

}
