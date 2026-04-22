package com.swp391.OnlineLearning.service.specification;

import com.swp391.OnlineLearning.model.CourseCategory;
import org.springframework.data.jpa.domain.Specification;

public class CourseCategorySpecs {
    public static Specification<CourseCategory> searchByName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(root.get("name"), "%" + name + "%");
        };
    }

    public static Specification<CourseCategory> searchByactive(Boolean active) {
        return (root, query, criteriaBuilder) -> {
            if (active == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("active"), active);
        };
    }
}
