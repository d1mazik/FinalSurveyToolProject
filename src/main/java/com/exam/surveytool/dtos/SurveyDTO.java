package com.exam.surveytool.dtos;

import lombok.Data;

@Data
public class SurveyDTO {
    private String title;
    private String description;
    private Long userId;
}