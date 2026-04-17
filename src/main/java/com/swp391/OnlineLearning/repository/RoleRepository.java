package com.swp391.OnlineLearning.repository;

import com.swp391.OnlineLearning.Model.UserRole;

import java.util.Optional;

public interface RoleRepository {
    Optional<UserRole> findByName(String roleName);
}
