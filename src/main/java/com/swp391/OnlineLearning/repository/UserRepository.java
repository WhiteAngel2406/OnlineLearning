package com.swp391.OnlineLearning.repository;

import com.swp391.OnlineLearning.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAll(Specification<User> specs, Pageable pageale);
}
