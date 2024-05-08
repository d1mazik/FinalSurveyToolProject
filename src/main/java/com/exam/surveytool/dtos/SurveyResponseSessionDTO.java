package com.exam.surveytool.dtos;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class SurveyResponseSessionDTO {
    private Long surveyId;
    private Long userId;
    private List<AnswerDTO> answers;
}
