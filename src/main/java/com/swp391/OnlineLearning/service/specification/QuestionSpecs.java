package com.swp391.OnlineLearning.service.specification;

import com.swp391.OnlineLearning.model.Question;
import org.springframework.data.jpa.domain.Specification;

public class QuestionSpecs {
    public static Specification<Question> findByType(String type) {
        return (root, query, cb) -> {
            if (type == null || type.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.equal(root.get("questionType"), type);
        };
    }

    public static Specification<Question> findByQuizId(Long quizId) {
        return (root, query, criteriaBuilder) -> {
            if (quizId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("lesson").get("id"), quizId);
        };
    }
}
