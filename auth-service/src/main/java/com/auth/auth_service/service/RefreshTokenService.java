package com.auth.auth_service.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

import org.springframework.stereotype.Service;

import com.auth.auth_service.dao.request.LogoutRequest;
import com.auth.auth_service.enums.ErrorCode;
import com.auth.auth_service.exception.CustomException;
import com.auth.auth_service.model.RefreshToken;
import com.auth.auth_service.model.Users;
import com.auth.auth_service.repository.RefreshTokenRepository;

import java.util.Base64;
import java.util.HexFormat;
import java.util.Optional;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class RefreshTokenService {
    
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional 
    public String createRefreshToken(Users user){
        byte[] randomBytes = new byte[32];
        new SecureRandom().nextBytes(randomBytes);

        String rawToken = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        String tokenHash = hashToken(rawToken);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken_hash(tokenHash);

         refreshToken.setExpires_at(
                java.time.Instant.now()
                        .plus(7, java.time.temporal.ChronoUnit.DAYS)
        );

        refreshToken.setToken_expired(false);

        refreshTokenRepository.save(refreshToken);

        return rawToken;
    }

    @Transactional
    public void userLogout(LogoutRequest request) {

        String tokenHash =hashToken(
                request.refreshToken()
        );

        Optional<RefreshToken> refreshToken =
                refreshTokenRepository.findByTokenHash(tokenHash);

        if (refreshToken == null) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        RefreshToken getRefreshToken=refreshToken.get();

        getRefreshToken.setToken_revoked(true);
        refreshTokenRepository.save(getRefreshToken);
    }


    private String hashToken(String token) {
        try {
            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(
                    token.getBytes(StandardCharsets.UTF_8)
            );

            return HexFormat.of().formatHex(hash);

        } catch (java.security.NoSuchAlgorithmException ex) {
            throw new IllegalStateException(
                    "SHA-256 algorithm unavailable", ex
            );
        }
    }

    @Transactional 
    public void deleteRefreshToken(String refreshToken) {
        String tokenHash = hashToken(refreshToken);
        refreshTokenRepository.deleteByTokenHash(tokenHash);
    }
}
