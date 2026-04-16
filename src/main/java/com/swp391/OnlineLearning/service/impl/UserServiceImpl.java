package com.swp391.OnlineLearning.service.impl;

import com.swp391.OnlineLearning.Model.User;
import com.swp391.OnlineLearning.repository.UserRepository;
import com.swp391.OnlineLearning.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
}
