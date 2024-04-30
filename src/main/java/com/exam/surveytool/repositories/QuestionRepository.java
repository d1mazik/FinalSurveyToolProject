package com.exam.surveytool.repositories;

import com.exam.surveytool.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findBySurveyId(Long surveyId); // Hämtar alla frågor baserat på surveyId
}
