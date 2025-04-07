package com.practice.websocketbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUserRequest {
    @NotBlank(message = "Username cannot be blank")
    private String fullName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    private String password;

    @JsonIgnore
    @Schema(hidden = true)
    private boolean isVerified;

    @JsonIgnore
    @Schema(hidden = true)
    private String otp;

    @JsonIgnore
    @Schema(hidden = true)
    private LocalDateTime otpCreatedAt;

    @JsonIgnore
    @Schema(hidden = true)
    private String resetToken;

    @JsonIgnore
    @Schema(hidden = true)
    private LocalDateTime resetTokenExpiry;

    @JsonIgnore
    @Schema(hidden = true)
    private int level;

    @JsonIgnore
    @Schema(hidden = true)
    private int experience;

    private String profile_image;

    @JsonIgnore
    @Schema(hidden = true)
    private LocalDate createdAt;
}
