package com.dsoft.service.impl;

import com.dsoft.dto.userdto.RegisterRequest;
import com.dsoft.dto.userdto.ResetPasswordRequest;
import com.dsoft.dto.userdto.UserResponse;
import com.dsoft.entity.User;
import com.dsoft.error.ResourceAlreadyExistsException;
import com.dsoft.error.ResourceNotFoundException;
import com.dsoft.repository.UserRepository;
import com.dsoft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new com.dsoft.error.ResourceAlreadyExistsException(
                    "User already exists with email: " + request.getEmail());
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        User savedUser = userRepository.save(user);

        return mapToUserResponse(savedUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToUserResponse)
                .toList();
    }

    @Override
    public UserResponse updateUser(Long id, RegisterRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        if (!user.getEmail().equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {

            throw new ResourceAlreadyExistsException(
                    "Email already exists: " + request.getEmail());
        }

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        User updatedUser = userRepository.save(user);

        return mapToUserResponse(updatedUser);
    }


    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        user.setDeleted(true);

        userRepository.save(user);
    }

    @Override
    public void resetPassword(Long userId, ResetPasswordRequest request) {

         User user =   userRepository.findByIdAndDeletedFalse(userId)
                     .orElseThrow(()-> new ResourceNotFoundException("User not found with id: " + userId ));

         user.setPassword(request.getNewPassword());

         userRepository.save(user);
    }


    // Mapper Method
    private UserResponse mapToUserResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
}