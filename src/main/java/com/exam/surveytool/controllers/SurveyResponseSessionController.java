package com.exam.surveytool.controllers;
import com.exam.surveytool.dtos.EndSessionDTO;
import com.exam.surveytool.dtos.SurveyResponseSessionDTO;
import com.exam.surveytool.models.SurveyResponseSession;
import com.exam.surveytool.services.SurveyResponseSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/session")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class SurveyResponseSessionController {

    private final SurveyResponseSessionService sessionService;

    @PostMapping("/start")
    public ResponseEntity<SurveyResponseSession> startSession(@RequestBody SurveyResponseSessionDTO sessionDTO) {
        SurveyResponseSession session = sessionService.startSession(sessionDTO);
        return ResponseEntity.ok(session);
    }

    @PostMapping("/end/{sessionId}")
    public ResponseEntity<?> endSession(@PathVariable Long sessionId) {
        try {
            EndSessionDTO session = sessionService.endSession(sessionId);
            return ResponseEntity.ok(session);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to end session: " + e.getMessage());
        }
    }

}
