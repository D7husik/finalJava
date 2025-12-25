package com.safetravels.service;

import com.safetravels.dto.request.UpdateUserRequest;
import com.safetravels.dto.response.UserResponse;
import com.safetravels.entity.User;

public interface UserService {
    UserResponse getCurrentUser(String email);

    UserResponse updateCurrentUser(String email, UpdateUserRequest request);

    User findUserByEmail(String email);
}
