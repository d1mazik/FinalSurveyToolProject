package com.exam.surveytool.controllers;

import com.exam.surveytool.dtos.AnswerDTO;
import com.exam.surveytool.models.Answer;
import com.exam.surveytool.services.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public ResponseEntity<Answer> createAnswer(@RequestBody AnswerDTO answerDTO) {
        try {
            Answer answer = answerService.createAnswer(answerDTO);
            return new ResponseEntity<>(answer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Answer> updateAnswer(@PathVariable Long id, @RequestBody AnswerDTO answerDTO) {
        try {
            Answer updatedAnswer = answerService.updateAnswer(id, answerDTO);
            return ResponseEntity.ok(updatedAnswer);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long id) {
        try {
            answerService.deleteAnswer(id);
            return ResponseEntity.ok().build();  // Returnerar OK om inga fel inträffar
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Answer not found with id: " + id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}