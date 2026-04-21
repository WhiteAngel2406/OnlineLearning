package com.swp391.OnlineLearning.Service;

import com.swp391.OnlineLearning.Model.QuizAttemptQuestion;

import java.util.List;

public interface QuizAttemptQuestionService{
    List<QuizAttemptQuestion> findAll();
    QuizAttemptQuestion findById(Long quizAttemptId);
    void save(QuizAttemptQuestion quizAttemptQuestion);
    void delete(QuizAttemptQuestion quizAttemptQuestion);
//    void saveQuestion(Long quizAttemptId, AnsweredOption answeredOption);
//    void removeSelectedOption(Long attemptQuestionId, QuizAttemptSelectedOption attemptSelectedOption);

}
