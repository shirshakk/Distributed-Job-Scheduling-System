package com.auth.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.auth_service.dao.request.LoginRequest;
import com.auth.auth_service.dao.request.LogoutRequest;
import com.auth.auth_service.dao.request.RegisterRequest;
import com.auth.auth_service.dao.response.ApiResponse;
import com.auth.auth_service.dao.response.LoginResponse;
import com.auth.auth_service.service.RefreshTokenService;
import com.auth.auth_service.service.UsersService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/auth")
public class UsersController {

    private  UsersService usersService;
    private RefreshTokenService refreshTokenService;

    public UsersController(UsersService usersService,RefreshTokenService refreshTokenService){
        this.usersService=usersService;
        this.refreshTokenService=refreshTokenService;
    }


    @PostMapping("/register")
    public ResponseEntity<Void> newUser(
        @Valid @RequestBody  RegisterRequest request
    ){
        usersService.newUser(request);

        
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> userLogin(
        @Valid @RequestBody LoginRequest request
    ){
        LoginResponse response = usersService.userLogin(request);

        return ResponseEntity.ok(ApiResponse.success("Login Success", response));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> userLogout(
        @Valid @RequestBody LogoutRequest request
    ){
        refreshTokenService.userLogout(request);
        return ResponseEntity.noContent().build();
    }
    
}
