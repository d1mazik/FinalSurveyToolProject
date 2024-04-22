package services;

import models.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.SurveyRepository;

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
    public Survey CreateSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    @Transactional
    public void deleteSurvey(Long id) {
        if (!surveyRepository.existsById(id)) {
            throw new NoSuchElementException("Survey with id " + id + " was not found");
        }
        surveyRepository.deleteById(id);
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
