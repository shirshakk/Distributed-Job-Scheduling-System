package com.auth.auth_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.auth_service.model.RefreshToken;

public interface RefreshTokenRepository extends  JpaRepository<RefreshToken,UUID>{
    
}
