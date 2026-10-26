package com.auth.auth_service.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.auth.auth_service.dao.request.LoginRequest;
import com.auth.auth_service.dao.request.LogoutRequest;
import com.auth.auth_service.dao.request.RegisterRequest;
import com.auth.auth_service.dao.response.LoginResponse;
import com.auth.auth_service.enums.ErrorCode;
import com.auth.auth_service.enums.UserRole;
import com.auth.auth_service.enums.UserStatus;
import com.auth.auth_service.exception.CustomException;
import com.auth.auth_service.model.Users;
import com.auth.auth_service.repository.UsersRepository;


@Service 
public class UsersService {
    private  UsersRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final RefreshTokenService refreshTokenService;

    public  UsersService(UsersRepository userRepository, PasswordEncoder passwordEncoder,JwtEncoder jwtEncoder,RefreshTokenService refreshTokenService){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtEncoder=jwtEncoder;
        this.refreshTokenService=refreshTokenService;
    }


    public Users newUser(RegisterRequest request) {

        Users newUser = new Users(
            request.user_name(),
            request.user_email(),
            passwordEncoder.encode(request.user_password()),
            UserRole.USER,
            UserStatus.ACTIVE
        );

        return userRepository.save(newUser);
    }



    public LoginResponse userLogin(LoginRequest request){
        Optional <Users> getUser= userRepository.findUserByEmail(request.email());
        if(getUser == null){
            throw new CustomException(ErrorCode.INVALID_EMAIL_ID);
        }

        Users user=getUser.get();

        if (!passwordEncoder.matches(request.password(), user.getUser_password())) {
            throw new IllegalArgumentException("Invalid password");
        }

         Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("transaction-auth")
                .issuedAt(now)
                .expiresAt(now.plus(15, ChronoUnit.MINUTES))
                .subject(user.getUser_id().toString())
                .claim("user-id", user.getUser_id())
                .claim("email", user.getUser_email())
                .build();

        String refreshToken =refreshTokenService.createRefreshToken(user);
        String accessToken = jwtEncoder.encode(
        JwtEncoderParameters.from(claims)
        ).getTokenValue();
        
        return new LoginResponse(accessToken,refreshToken);

    }

    
}
