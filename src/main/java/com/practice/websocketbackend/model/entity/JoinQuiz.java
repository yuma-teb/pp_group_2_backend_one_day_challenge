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
public class JoinQuiz {
    private Long joinId;
    private Long quizId;
    private Long studentId;
    private Long sessionId;
    private LocalDate startTime;
    private LocalDate endTime;
    private Float archiveScore;
    private Boolean isJoined;
}
