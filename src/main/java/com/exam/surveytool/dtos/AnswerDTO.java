package com.exam.surveytool.dtos;

import lombok.Data;

@Data
public class AnswerDTO {
        private Long questionId;
        private Long surveyResponseSessionId;
        private String text;
        private Long selectedOption;
        private Integer scale;
        // Getters och setters
}
