package com.swp391.OnlineLearning.repository;

import com.swp391.OnlineLearning.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndEnabledTrue(String email);
    boolean existsByEmail(String email);
    List<User> findAllByRole(com.swp391.OnlineLearning.model.UserRole role);
}
