package com.dsoft.service;

import com.dsoft.dto.AuthResponse;
import com.dsoft.dto.userdto.LoginRequest;
import com.dsoft.dto.userdto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}