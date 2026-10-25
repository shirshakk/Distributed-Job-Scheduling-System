package com.auth.auth_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.auth_service.model.Users;

@Repository 
public interface  UsersRepository extends JpaRepository<Users,UUID>{
    
}
