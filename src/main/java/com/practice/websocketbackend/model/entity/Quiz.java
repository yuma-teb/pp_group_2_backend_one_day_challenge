package com.practice.websocketbackend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quiz {
    private Long quizId;
    private String title;
    private String description;
    private Long creatorId;
    private LocalDate creationAt;
    private Boolean isSchedule;
    private Integer timeLimit;
    private String accessCode;
    private Long subjectId;
    private Integer totalScore;
}
