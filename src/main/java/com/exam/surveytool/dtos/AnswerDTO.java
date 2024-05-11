package com.exam.surveytool.dtos;

import lombok.Data;

@Data
public class AnswerDTO {
    private Long id;
    private Long questionId;
    private Long sessionId;
    private String text;          // För textbaserade svar
    private Long selectedOption;  // För optionsbaserade svar, ID för det valda alternativet
    private Integer selectedScale;        // För skalabaserade svar
}
