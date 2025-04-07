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
public class Session {
    private Long sessionId;
    private Long quizId;
    private Long hostId;
    private LocalDate startTime;
    private LocalDate endTime;
}