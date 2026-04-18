package com.cg.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private String details;
    private Map<String, String> fieldErrors;

    public ErrorResponse(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public ErrorResponse(LocalDateTime timestamp, String message, String details, Map<String, String> fieldErrors) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.fieldErrors = fieldErrors;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public String getMessage() { return message; }
    public String getDetails() { return details; }
    public Map<String, String> getFieldErrors() { return fieldErrors; }
}