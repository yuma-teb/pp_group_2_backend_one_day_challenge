package com.practice.websocketbackend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Option {
    private Long optionId;
    private Long questionId;
    private String optionText;
    private Boolean isCorrect;
}