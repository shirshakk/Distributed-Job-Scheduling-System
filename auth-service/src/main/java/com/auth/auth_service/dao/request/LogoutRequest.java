package com.auth.auth_service.dao.request;

import jakarta.validation.constraints.NotBlank;

public record LogoutRequest(
    @NotBlank(message = "Refresh token is required")
    String refreshToken
) {
    
}
