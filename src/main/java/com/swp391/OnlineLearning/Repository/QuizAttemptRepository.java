package com.swp391.OnlineLearning.Repository;

import com.swp391.OnlineLearning.Model.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizAttemptRepository extends JpaRepository <QuizAttempt, Long>{
//    QuizAttempt findByUserIdAndQuizId(Long userId, Long quizId);
//    List<QuizAttempt> findByUserId(Long userId);
}
