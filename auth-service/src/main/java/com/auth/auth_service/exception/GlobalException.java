package com.auth.auth_service.exception;

import java.time.Instant;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth.auth_service.dao.response.ApiErrorResponse;
import com.auth.auth_service.enums.ErrorCode;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice 
public class GlobalException {
    
    public ResponseEntity<ApiErrorResponse> buildError(
        HttpStatus status,
        String code,
        String message,
        HttpServletRequest request
    ){
       String requestId = request.getHeader("X-REQUEST-ID");
       if(requestId == null || requestId.isEmpty()){
            requestId= UUID.randomUUID().toString();
       }

       ApiErrorResponse response = new ApiErrorResponse(
                new ApiErrorResponse.ErrorDetail(
                        code, message, requestId
                ),
                Instant.now()
        );

        return ResponseEntity.status(status).body(response);
    }


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiErrorResponse> handleCustom(
        CustomException ex, 
        HttpServletRequest request
    ){
        ErrorCode code = ex.getErrorCode();
        return buildError(code.getStatus(), code.name(), code.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getDefaultMessage())
                .findFirst()
                .orElse("Invalid input");

        return buildError(HttpStatus.BAD_REQUEST, "VALIDATION_FAILED", message, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpected(
            Exception ex, HttpServletRequest request) {

       
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR",
                "Something went wrong", request);
    }
}
