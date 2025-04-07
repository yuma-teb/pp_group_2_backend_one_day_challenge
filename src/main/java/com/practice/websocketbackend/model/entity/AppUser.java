package com.practice.websocketbackend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser implements UserDetails {
    private UUID userId;
    private String fullName;
    private String email;
    private String password;
    private boolean isVerified;
    private String otp;
    private LocalDateTime otpCreatedAt;
    // New fields for password reset
    private String resetToken;
    private LocalDateTime resetTokenExpiry;
    private List<String> roles;
    // New fields requirement
    private int level;
    private int experience;
    private String profile_image;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
