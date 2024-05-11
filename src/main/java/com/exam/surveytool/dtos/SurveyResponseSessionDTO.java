package com.exam.surveytool.dtos;

import lombok.Data;

import java.util.List;

@Data
public class SurveyResponseSessionDTO {
    private Long surveyId;
    private Long userId;
    private List<AnswerDTO> answers;
}
