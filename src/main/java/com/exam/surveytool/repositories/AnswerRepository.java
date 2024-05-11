package com.exam.surveytool.repositories;

import com.exam.surveytool.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestionId(Long questionId);
    List<Answer> findBySessionId(Long sessionId);

}
