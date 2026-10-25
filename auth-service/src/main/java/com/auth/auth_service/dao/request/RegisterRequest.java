package com.auth.auth_service.dao.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotNull(message = "Enter the user email")
    @Email(message = "Enter a valid email address")
    String user_email,
    @NotNull (message = "Enter the password")
    @Size(min = 8, message = "Password must be at least 8 characters")
    String user_password,
    @NotNull (message = "Enter the user name")
    String user_name
    
){}
