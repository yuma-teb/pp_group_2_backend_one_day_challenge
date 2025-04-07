package com.practice.websocketbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation Failed");

        // Collect field validation errors
        HashMap<Object, Object> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        // Add errors to ProblemDetail as extra properties
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ProblemDetail handleMethodValidationException(HandlerMethodValidationException e) {
        HashMap<Object, Object> errors = new HashMap<>();

        // Loop through each invalid parameter validation result
        e.getParameterValidationResults().forEach(parameterError -> {
            String paramName = parameterError.getMethodParameter().getParameterName(); // Get parameter name

            // Loop through each validation error message for this parameter
            for (var messageError : parameterError.getResolvableErrors()) {
                errors.put(paramName, messageError.getDefaultMessage()); // Store error message
            }
        });

        // Create structured ProblemDetail response
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Method Parameter Validation Failed");
        problemDetail.setProperties(Map.of("timestamp", LocalDateTime.now(), "errors", errors // Attach validation errors
        ));

        return problemDetail;
    }

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolationException(
            jakarta.validation.ConstraintViolationException ex,
            WebRequest request) {

        // Create a standardized error response structure
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "The request contains invalid parameters"
        );

        problemDetail.setTitle("Parameter Validation Error");

        // Create structured error details
        List<Map<String, Object>> violations = new ArrayList<>();

        ex.getConstraintViolations().forEach(violation -> {
            Map<String, Object> violationDetails = new HashMap<>();

            // Extract parameter info
            String path = violation.getPropertyPath().toString();
            String[] pathParts = path.split("\\.");
            String paramName = pathParts.length > 1 ? pathParts[1] : path;

            violationDetails.put("field", paramName);
            violationDetails.put("message", violation.getMessage());
            violationDetails.put("rejectedValue", violation.getInvalidValue());

            // Add machine-readable code for automated client handling
            String constraintType = violation.getConstraintDescriptor().getAnnotation()
                    .annotationType().getSimpleName();
            violationDetails.put("code", "INVALID_" + constraintType.toUpperCase());

            violations.add(violationDetails);
        });

        // Add metadata to help debugging and tracking
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("timestamp", OffsetDateTime.now().toString());
        metadata.put("requestId", UUID.randomUUID().toString());  // Ideally from request context
        metadata.put("path", request.getDescription(false).substring(4));  // Remove "uri="

        // Add all details to problem
        problemDetail.setProperty("violations", violations);
        problemDetail.setProperty("metadata", metadata);

        // Return with appropriate HTTP status
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problemDetail);
    }

}