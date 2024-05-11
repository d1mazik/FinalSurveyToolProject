package com.exam.surveytool.controllers;

import com.exam.surveytool.dtos.AnswerDTO;
import com.exam.surveytool.dtos.EndSessionRequest;
import com.exam.surveytool.dtos.SurveyResponseSessionDTO;
import com.exam.surveytool.models.Answer;
import com.exam.surveytool.models.SurveyResponseSession;
import com.exam.surveytool.services.AnswerService;
import com.exam.surveytool.services.SurveyResponseSessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SurveyResponseSessionController {

    private final SurveyResponseSessionService sessionService;
    private final AnswerService answerService;

    @Autowired
    public SurveyResponseSessionController(SurveyResponseSessionService sessionService, SurveyResponseSessionService sessionService1, AnswerService answerService) {
        this.sessionService = sessionService1;
        this.answerService = answerService;
    }

    // Endpoint to start a session
    @PostMapping("/start")
    public ResponseEntity<SurveyResponseSession> startSession(@RequestBody SurveyResponseSessionDTO sessionDTO) {
        SurveyResponseSession session = sessionService.startSession(sessionDTO);
        return ResponseEntity.ok(session);
    }
    @PostMapping("/add-answer")
    public ResponseEntity<AnswerDTO> addAnswer(@RequestBody AnswerDTO answerDTO) {
        try {
            // Anropa servicemetoden som returnerar en DTO
            AnswerDTO createdAnswerDTO = answerService.createAnswer(answerDTO);
            // Returnera DTO med statuskod 201 CREATED
            return new ResponseEntity<>(createdAnswerDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            // Hantera potentiella fel, till exempel genom att logga dem och returnera en lämplig felstatus
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to end a session
    @PostMapping("/end-session")
    public ResponseEntity<Void> endSession(@RequestBody EndSessionRequest request) {
        sessionService.endSession(request.getSessionId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{sessionId}/answers")
    public ResponseEntity<List<AnswerDTO>> getAnswersForSession(@PathVariable Long sessionId) {
        try {
            List<AnswerDTO> answers = sessionService.getAnswersForSession(sessionId);
            return ResponseEntity.ok(answers);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


}
