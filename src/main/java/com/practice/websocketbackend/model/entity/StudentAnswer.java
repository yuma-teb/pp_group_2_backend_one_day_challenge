package com.practice.websocketbackend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentAnswer {
    private Long answerId;
    private Long joinId;
    private Long questionId;
    private Long optionId;
}
