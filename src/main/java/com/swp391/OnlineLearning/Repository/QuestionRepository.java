package com.swp391.OnlineLearning.Repository;

import com.swp391.OnlineLearning.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question> {
    @Query("SELECT q FROM Question q LEFT JOIN FETCH q.answerOptions WHERE q.id = :id")
    Optional<Question> findByIdWithAnswerOptions(@Param("id") Long id);
}
