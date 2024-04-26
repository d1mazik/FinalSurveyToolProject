package com.exam.surveytool.repositories;

import com.exam.surveytool.models.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
}

