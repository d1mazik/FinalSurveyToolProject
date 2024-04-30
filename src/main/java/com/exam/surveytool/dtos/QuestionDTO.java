package com.exam.surveytool.dtos;

import com.exam.surveytool.enums.EQuestionType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.util.List;

//DTO som inputparameter i mina controllermetoder för att bättre hantera
//olika typer av input beroende på frågetyp
//denna DTO används i QuestionController
@Data
public class QuestionDTO {
    private Long id;
    @NotNull
    private EQuestionType type;
    @NotEmpty
    private String text;
    private List<String> options;
    @NotNull
    private Integer minScale;
    @NotNull
    private Integer maxScale;
    @NotNull
    private Long surveyId;
}
