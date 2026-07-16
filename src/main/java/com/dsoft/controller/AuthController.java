package com.dsoft.controller;

import com.dsoft.dto.AuthResponse;
import com.dsoft.dto.userdto.ChangePasswordRequest;
import com.dsoft.dto.userdto.LoginRequest;
import com.dsoft.dto.userdto.RegisterRequest;
import com.dsoft.dto.ResponseApi;
import com.dsoft.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseApi<AuthResponse>> register(@RequestBody RegisterRequest request){

       AuthResponse authResponse = authService.register(request);

        ResponseApi<AuthResponse> responseApi =  ResponseApi.<AuthResponse>builder()
                .success(true)
                .status(HttpStatus.CREATED.value())
                .message("User registered successfully.")
                .data(authResponse)
                .timeStamp(LocalTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(responseApi);
    }
    // Login User
    @PostMapping("/login")
    public ResponseEntity<ResponseApi<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse authResponse = authService.login(request);

        ResponseApi<AuthResponse> response = ResponseApi.<AuthResponse>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Login successful.")
                .data(authResponse)
                .timeStamp(LocalTime.now())
                .build();

        return ResponseEntity.ok(response);
    }


    @PatchMapping("/change-password")
    public ResponseEntity<ResponseApi<Void>> changePassword(
            @Valid @RequestBody ChangePasswordRequest request) {

        authService.changePassword(request);

        ResponseApi<Void> response = ResponseApi.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Password changed successfully.")
                .data(null)
                .timeStamp(LocalTime.now())
                .build();

        return ResponseEntity.ok(response);
    }
}




