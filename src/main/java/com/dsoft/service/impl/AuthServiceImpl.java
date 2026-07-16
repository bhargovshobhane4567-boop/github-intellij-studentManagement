package com.dsoft.service.impl;

import com.dsoft.dto.userdto.ChangePasswordRequest;
import com.dsoft.security.JwtService;
import com.dsoft.dto.AuthResponse;
import com.dsoft.dto.userdto.LoginRequest;
import com.dsoft.dto.userdto.RegisterRequest;
import com.dsoft.entity.User;
import com.dsoft.enums.Role;
import com.dsoft.error.BadRequestException;
import com.dsoft.error.ResourceAlreadyExistsException;
import com.dsoft.error.ResourceNotFoundException;
import com.dsoft.repository.UserRepository;
import com.dsoft.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private  final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException(
                    "User already exists with email: " + request.getEmail());
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .role(request.getRole())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {

     User user = userRepository.findByEmail(request.getEmail())
             .orElseThrow(()->new ResourceNotFoundException("User not found with this emailId "+request.getEmail()));

     if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
         throw new BadRequestException("invalid password or email Id");
     }
       String token = jwtService.generateToken(user);

        return  AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }
}
