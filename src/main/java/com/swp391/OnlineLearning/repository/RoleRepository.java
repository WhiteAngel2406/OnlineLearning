package com.swp391.OnlineLearning.repository;

import com.swp391.OnlineLearning.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByName(String roleName);
}
