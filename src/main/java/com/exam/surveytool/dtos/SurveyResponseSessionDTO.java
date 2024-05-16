package com.exam.surveytool.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class SurveyResponseSessionDTO {
    private Long surveyId;
    private Long userId;
}
