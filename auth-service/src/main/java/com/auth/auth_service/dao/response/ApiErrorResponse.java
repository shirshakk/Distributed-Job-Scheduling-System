package com.auth.auth_service.dao.response;

import java.time.Instant;

public record ApiErrorResponse(
    ErrorDetail error,
    Instant timestamp 
) {
    public record ErrorDetail(
            String code,
            String message,
            String requestId
    ) {}
    
}

