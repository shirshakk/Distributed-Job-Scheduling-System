package com.auth.auth_service.dao.response;

import com.auth.auth_service.enums.UserRole;
import com.auth.auth_service.enums.UserStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record UserProfileResponse(

    String user_email,

    String user_name,

    @Enumerated(EnumType.STRING)
    UserRole user_role,

    @Enumerated(EnumType.STRING)
    UserStatus user_status
) {
    
}
