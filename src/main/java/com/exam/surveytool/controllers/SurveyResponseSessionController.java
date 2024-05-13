package com.exam.surveytool.controllers;

import com.exam.surveytool.dtos.SurveyResponseSessionDTO;
import com.exam.surveytool.models.SurveyResponseSession;
import com.exam.surveytool.services.SurveyResponseSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/session")
public class SurveyResponseSessionController {

    private final SurveyResponseSessionService sessionService;

    @Autowired
    public SurveyResponseSessionController(SurveyResponseSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/start")
    public ResponseEntity<SurveyResponseSession> startSession(@RequestBody SurveyResponseSessionDTO sessionDTO) {
        SurveyResponseSession session = sessionService.startSession(sessionDTO);
        return ResponseEntity.ok(session);
    }

    @PostMapping("/end/{sessionId}")
    public ResponseEntity<SurveyResponseSession> endSession(@PathVariable Long sessionId) {
        sessionService.endSession(sessionId);
        return ResponseEntity.ok().build();
    }

}
