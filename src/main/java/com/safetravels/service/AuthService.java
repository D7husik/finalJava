package com.safetravels.service;

import com.safetravels.dto.request.LoginRequest;
import com.safetravels.dto.request.RegisterRequest;
import com.safetravels.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
