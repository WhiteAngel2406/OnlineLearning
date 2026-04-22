package com.swp391.OnlineLearning.repository;

import com.swp391.OnlineLearning.model.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizAttemptRepository extends JpaRepository <QuizAttempt, Long>{
//    QuizAttempt findByUserIdAndQuizId(Long userId, Long quizId);
//    List<QuizAttempt> findByUserId(Long userId);
}
