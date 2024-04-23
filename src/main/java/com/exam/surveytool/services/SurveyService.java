package com.exam.surveytool.services;

import com.exam.surveytool.models.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.exam.surveytool.repositories.SurveyRepository;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class SurveyService {
    private final SurveyRepository surveyRepository;

    @Autowired
    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Transactional
    public Survey createSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    @Transactional
    public void deleteSurvey(Long id) {
        if (!surveyRepository.existsById(id)) {
            throw new NoSuchElementException("Survey with id " + id + " was not found");
        }
        surveyRepository.deleteById(id);
    }

    @Transactional
    public Survey updateSurvey(Long id, Survey surveyDetails) {
        Survey existingSurvey = surveyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Survey with id: " + id + " was not found"));

        // Uppdatera de fält som behövs från surveyDetails
        existingSurvey.setTitle(surveyDetails.getTitle());
        existingSurvey.setDescription(surveyDetails.getDescription());

        // onSave eller onUpdate metoder i Survey-klassen kommer automatiskt att hantera createdAt och updatedAt.
        return surveyRepository.save(existingSurvey);
    }

    @Transactional(readOnly = true)
    public Survey getSurveyById(Long id) {
        return surveyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Survey with id: " + id + " was not found"));
    }

    @Transactional(readOnly = true)
    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }


}
