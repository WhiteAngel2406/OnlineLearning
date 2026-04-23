package com.swp391.OnlineLearning.Repository;

import com.swp391.OnlineLearning.Model.QuizAttemptQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizAttemptQuestionRepository extends JpaRepository <QuizAttemptQuestion, Long>{
//    QuizAttemptQuestion findByQuizAttemptIdAndQuestionId(Long quizAttemptId, Long questionId);
}
