package com.swp391.OnlineLearning.repository;

import com.swp391.OnlineLearning.model.CourseCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CourseCategoryRepository extends JpaRepository<CourseCategory, Long> {
    Page<CourseCategory> findAll(Specification<CourseCategory> courseCategorySpecification, Pageable pageable);
    List<CourseCategory> findAllByActiveTrue();
}
