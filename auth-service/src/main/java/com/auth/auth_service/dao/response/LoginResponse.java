package com.auth.auth_service.dao.response;

public record LoginResponse(
    String accessToken,
    String refreshToken
) {}
