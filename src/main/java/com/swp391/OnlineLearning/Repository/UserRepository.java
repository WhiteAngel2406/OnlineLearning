package com.swp391.OnlineLearning.Repository;

import com.swp391.OnlineLearning.Model.User;
import com.swp391.OnlineLearning.Model.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndEnabledTrue(String email);
    Page<User> findAll(Specification<User> specs, Pageable pageale);
    List<User> findAllByRole(UserRole role);
    long countByRole_Name(String roleName);
}
