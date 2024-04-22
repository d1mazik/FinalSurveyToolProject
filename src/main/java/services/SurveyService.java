package services;

import models.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import repositories.SurveyRepository;


@Service
public class SurveyService {
    private final SurveyRepository surveyRepository;

    @Autowired
    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Transactional
    public Survey CreateSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    @Transactional
    public void deleteSurvey(Long id) {
        surveyRepository.deleteById(id);
    }



}
