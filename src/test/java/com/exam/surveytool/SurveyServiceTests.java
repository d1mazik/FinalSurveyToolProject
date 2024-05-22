package com.exam.surveytool;

import com.exam.surveytool.dtos.SurveyDTO;
import com.exam.surveytool.models.Survey;
import com.exam.surveytool.models.User;
import com.exam.surveytool.repositories.SurveyRepository;
import com.exam.surveytool.repositories.UserRepository;
import com.exam.surveytool.services.SurveyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SurveyServiceTests {

    @Mock
    private SurveyRepository surveyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SurveyService surveyService;


    @Test
    void createSurvey_whenSuccessfullyCreated_shouldSurveyRepositorySave() {

        SurveyDTO surveyDTO = new SurveyDTO();
        surveyDTO.setTitle("Test Survey");
        surveyDTO.setDescription("Description");
        surveyDTO.setUserId(1L);

        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        surveyService.createSurvey(surveyDTO);

        verify(surveyRepository).save(any(Survey.class));
    }

    @Test
    void createSurvey_whenUserNotFound_shouldThrowRuntimeException() {

        SurveyDTO surveyDTO = new SurveyDTO();
        surveyDTO.setTitle("Test Survey");
        surveyDTO.setDescription("Description");
        surveyDTO.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            surveyService.createSurvey(surveyDTO);
        });

        assertEquals("User not found with id: 1", exception.getMessage());
    }

    @Test
    void deleteSurvey_whenSurveyExists_shouldSurveyRepositoryDelete() {

        Long surveyId = 1L;
        when(surveyRepository.existsById(surveyId)).thenReturn(true);

        surveyService.deleteSurvey(surveyId);

        verify(surveyRepository).deleteById(surveyId);
    }

    @Test
    void deleteSurvey_whenSurveyNotFound_shouldThrowNoSuchElementException() {

        Long surveyId = 1L;
        when(surveyRepository.existsById(surveyId)).thenReturn(false);

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            surveyService.deleteSurvey(surveyId);
        });

        assertEquals("Survey with id 1 was not found", exception.getMessage());
    }


    @Test
    void updateSurvey_whenSurveyExists_shouldSurveyRepositorySave() {

        Long surveyId = 2L;
        SurveyDTO surveyDTO = new SurveyDTO();
        surveyDTO.setTitle("Updated Title");
        surveyDTO.setDescription("Updated Description");

        Survey existingSurvey = new Survey();
        existingSurvey.setId(surveyId);
        existingSurvey.setTitle("Old Title");
        existingSurvey.setDescription("Old Description");

        when(surveyRepository.findById(surveyId)).thenReturn(Optional.of(existingSurvey));

        surveyService.updateSurvey(surveyId, surveyDTO);

        verify(surveyRepository).save(existingSurvey);
        assertEquals("Updated Title", existingSurvey.getTitle());
        assertEquals("Updated Description", existingSurvey.getDescription());
    }

    @Test
    void updateSurvey_whenSurveyNotFound_shouldThrowNoSuchElementException() {

        Long surveyId = 1L;
        SurveyDTO surveyDTO = new SurveyDTO();
        surveyDTO.setTitle("Updated Title");
        surveyDTO.setDescription("Updated Description");

        when(surveyRepository.findById(surveyId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            surveyService.updateSurvey(surveyId, surveyDTO);
        });

        assertEquals("Survey with id: 1 was not found", exception.getMessage());
    }



}
