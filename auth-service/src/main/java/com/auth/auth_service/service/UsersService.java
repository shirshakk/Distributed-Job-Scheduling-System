package com.auth.auth_service.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.auth_service.dao.request.RegisterRequest;
import com.auth.auth_service.enums.UserRole;
import com.auth.auth_service.enums.UserStatus;
import com.auth.auth_service.model.Users;
import com.auth.auth_service.repository.UsersRepository;

@Service 
public class UsersService {
    private  UsersRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public  UsersService(UsersRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
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

}
