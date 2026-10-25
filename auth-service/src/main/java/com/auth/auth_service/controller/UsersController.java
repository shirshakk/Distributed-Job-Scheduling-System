package com.auth.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.auth_service.dao.request.RegisterRequest;
import com.auth.auth_service.service.UsersService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/auth")
public class UsersController {

    private  UsersService usersService;

    public UsersController(UsersService usersService){
        this.usersService=usersService;
    }


    @PostMapping("/register")
    public ResponseEntity<Void> newUser(
        @Valid @RequestBody  RegisterRequest request
    ){
        usersService.newUser(request);

        
        return ResponseEntity.noContent().build();
    }

    
}
