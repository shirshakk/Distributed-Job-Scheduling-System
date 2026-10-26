package com.auth.auth_service.enums;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter 
@AllArgsConstructor 
public enum ErrorCode{

    INVALID_EMAIL_ID("email id not registered",HttpStatus.NOT_FOUND),
    INVALID_PASSWORD("invalid password",HttpStatus.BAD_REQUEST),
    INVALID_REFRESH_TOKEN("Invalid token",HttpStatus.BAD_REQUEST);
    private final String message;
    private final HttpStatus status;
    
}
