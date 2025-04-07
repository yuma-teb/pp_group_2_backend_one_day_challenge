package com.practice.websocketbackend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    private Long questionId;
    private Long quizId;
    private String questionText;
    private Boolean isQcm;
    private Integer points;
}