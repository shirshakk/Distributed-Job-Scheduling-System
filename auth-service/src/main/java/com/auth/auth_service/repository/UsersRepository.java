package com.auth.auth_service.repository;

import java.util.UUID;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auth.auth_service.model.Users;

@Repository 
public interface  UsersRepository extends JpaRepository<Users,UUID>{
    @Query("SELECT u FROM Users u WHERE u.user_email = :email")
    Optional<Users> findUserByEmail(@Param("email") String email);
}
