package com.safetravels.service.impl;

import com.safetravels.dto.request.UpdateUserRequest;
import com.safetravels.dto.response.UserResponse;
import com.safetravels.entity.User;
import com.safetravels.exception.ResourceNotFoundException;
import com.safetravels.mapper.UserMapper;
import com.safetravels.repository.UserRepository;
import com.safetravels.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse getCurrentUser(String email) {
        User user = findUserByEmail(email);
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateCurrentUser(String email, UpdateUserRequest request) {
        User user = findUserByEmail(email);
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user = userRepository.save(user);
        return userMapper.toResponse(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
