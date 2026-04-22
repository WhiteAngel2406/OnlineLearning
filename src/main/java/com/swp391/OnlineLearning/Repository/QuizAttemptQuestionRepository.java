package com.swp391.OnlineLearning.repository;

import com.swp391.OnlineLearning.model.QuizAttemptQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizAttemptQuestionRepository extends JpaRepository <QuizAttemptQuestion, Long>{
//    QuizAttemptQuestion findByQuizAttemptIdAndQuestionId(Long quizAttemptId, Long questionId);
}
