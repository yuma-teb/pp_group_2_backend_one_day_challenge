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
public class SessionParticipant {
    private Long sessionId;
    private Long studentId;
    private LocalDate joinTime;
    private Boolean isJoined;
}