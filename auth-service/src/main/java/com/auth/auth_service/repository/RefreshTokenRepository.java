
package com.auth.auth_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.auth.auth_service.model.RefreshToken;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, UUID> {

    @Query("""
        SELECT r FROM RefreshToken r
        WHERE r.token_hash = :tokenHash
        """)
    Optional<RefreshToken> findByTokenHash(
            @Param("tokenHash") String tokenHash
    );

    @Modifying
    @Query("""
        DELETE FROM RefreshToken r
        WHERE r.token_hash = :tokenHash
        """)
    void deleteByTokenHash(
            @Param("tokenHash") String tokenHash
    );
}
