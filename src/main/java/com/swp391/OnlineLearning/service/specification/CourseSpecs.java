package com.swp391.OnlineLearning.service.specification;

import com.swp391.OnlineLearning.model.Course;
import com.swp391.OnlineLearning.model.CourseCategory;
import com.swp391.OnlineLearning.model.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class CourseSpecs {
    public static Specification<Course> hasStatus(Course.CourseStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Course> hasNameContaining(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("name"), "%" + keyword + "%");
        };
    }

    public static Specification<Course> hasCategoryId(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return criteriaBuilder.conjunction();
            }
            Join<Course, CourseCategory> categoryJoin = root.join("category");
            return criteriaBuilder.equal(categoryJoin.get("id"), categoryId);
        };
    }

    public static Specification<Course> hasAuthorId(Long authorId) {
        return (root, query, criteriaBuilder) -> {
            if (authorId == null) {
                return criteriaBuilder.conjunction();
            }
            Join<Course, User> authorJoin = root.join("author");
            return criteriaBuilder.equal(authorJoin.get("id"), authorId);
        };
    }
}
