package com.auth.auth_service.dao.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(

    @NotNull (message = "Email can not be null")
    @Email (message = "Enter the valid email")
    String email,

    @NotNull (message = "Password can not be null")
    String password

) {
    
}
