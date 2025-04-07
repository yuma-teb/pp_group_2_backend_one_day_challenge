package com.practice.websocketbackend.service;

import com.practice.websocketbackend.model.entity.AppUserRequest;
import com.practice.websocketbackend.model.response.AppUserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {
    AppUserResponse register(AppUserRequest request);
    void verify(String email, String otp);

    void resendOtp(String email);

    void requestPasswordReset(String email);

    void resetPassword(String resetToken, String newPassword);
}
