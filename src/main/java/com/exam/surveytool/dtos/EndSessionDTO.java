package com.exam.surveytool.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EndSessionDTO {
    private LocalDateTime endedAt;
    private Boolean isActive;
}