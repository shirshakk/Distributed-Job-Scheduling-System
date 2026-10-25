
package com.auth.auth_service.model;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.auth.auth_service.enums.UserRole;
import com.auth.auth_service.enums.UserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data 
@NoArgsConstructor 
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID user_id;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email")
    @Column(name = "user_email", unique = true, nullable = false)
    private String user_email;

    @NotBlank(message = "Password is required")
    @Column(name = "user_password", nullable = false)
    private String user_password;

    @NotBlank(message = "Name is required")
    @Column(name = "user_name", nullable = false)
    private String user_name;

    @NotNull(message = "Role is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole user_role;

    @NotNull(message = "User status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", nullable = false)
    private UserStatus user_status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updated_at;

    public Users(
        String user_name,
        String user_email,
        String user_password,
        UserRole user_role,
        UserStatus user_status
    ){
        this.user_name=user_name;
        this.user_email=user_email;
        this.user_password=user_password;
        this.user_status=user_status;
        this.user_role=user_role;
    }
}
