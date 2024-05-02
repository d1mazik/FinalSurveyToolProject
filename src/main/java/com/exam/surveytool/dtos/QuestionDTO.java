package com.exam.surveytool.dtos;

import com.exam.surveytool.enums.EQuestionType;
import lombok.Data;

import java.util.List;

//DTO som inputparameter i mina controllermetoder för att bättre hantera
//olika typer av input beroende på frågetyp
//denna DTO används i QuestionController
@Data
public class QuestionDTO {
    private Long id;
    private Long surveyId;
    private EQuestionType type;
    private String text;
    private List<String> options;
    private Integer minScale;
    private Integer maxScale;
}
