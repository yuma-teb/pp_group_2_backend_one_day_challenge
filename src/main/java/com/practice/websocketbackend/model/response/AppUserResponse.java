package com.practice.websocketbackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUserResponse {
    private UUID id;

    private String fullName;

    private String email;

    private boolean isVerified;

    private String otp;

    private LocalDateTime otpCreatedAt;

    private String resetToken;

    private LocalDateTime resetTokenExpiry;

    private List<String> roles;

    // New fields requirement
    private int level;
    private int experience;
    private String profile_image;
    private LocalDate createdAt;
}
