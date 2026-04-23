package com.swp391.OnlineLearning.Repository;

import com.swp391.OnlineLearning.Model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findAll(Specification<Course> spec, Pageable pageable);

    boolean existsByName(String name);

    List<Course> findAllByFeaturedTrue();

    @org.springframework.data.jpa.repository.Query("SELECT c.category.name, COUNT(c) FROM Course c GROUP BY c.category.name")
    List<Object[]> countCoursesByCategory();

    List<Course> findTop5ByOrderByCreatedAtDesc();
}
