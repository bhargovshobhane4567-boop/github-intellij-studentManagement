package com.dsoft.service;

import com.dsoft.dto.userdto.RegisterRequest;
import com.dsoft.dto.userdto.ResetPasswordRequest;
import com.dsoft.dto.userdto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(RegisterRequest request);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, RegisterRequest request);

    void deleteUser(Long id);

    void resetPassword(Long userId, ResetPasswordRequest request);

}