package com.practice.websocketbackend.controller;

import com.practice.websocketbackend.exception.BadRequestException;
import com.practice.websocketbackend.exception.NotFoundException;
import com.practice.websocketbackend.jwt.JwtService;
import com.practice.websocketbackend.model.entity.AppUserRequest;
import com.practice.websocketbackend.model.entity.AuthRequest;
import com.practice.websocketbackend.model.response.AuthResponse;
import com.practice.websocketbackend.service.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auths")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    /**
     * Authenticates a user by email and password.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthRequest request) throws Exception {
        authenticate(request.getEmail(), request.getPassword());
        System.out.println("ertyuio" + request);

        final UserDetails userDetails = appUserService.loadUserByUsername(request.getEmail());

        final String token = jwtService.generateToken(userDetails);
        AuthResponse authResponse = new AuthResponse(token);
        return ResponseEntity.ok(authResponse);
    }

    /**
     * Registers a new user.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AppUserRequest request) {
        return ResponseEntity.ok(appUserService.register(request));
    }

    /**
     * Verifies a user's email using the provided OTP.
     */
    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam String email, @RequestParam String otp) {
        try {
            appUserService.verify(email, otp);
            return ResponseEntity.ok("Email verified successfully!");
        } catch (NotFoundException | BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendOtp(@RequestParam String email) {
        appUserService.resendOtp(email);
        return ResponseEntity.ok("A new OTP has been sent to your email.");
    }

    /**
     * Helper method to authenticate a user.
     */
    private void authenticate(String email, String password) throws Exception {
        try {
            System.out.println("authenticating user");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            System.out.println("authenticated user");
        } catch (DisabledException e) {
            System.out.println("User disabled: " + email);
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            System.out.println("Invalid credentials for email: " + email);
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        appUserService.requestPasswordReset(email);
        return ResponseEntity.ok("Password reset email sent successfully");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam String resetToken,
            @RequestParam String newPassword) {
        appUserService.resetPassword(resetToken, newPassword);
        return ResponseEntity.ok("Password reset successfully");
    }
}