package com.swp391.OnlineLearning.service;

import com.swp391.OnlineLearning.model.QuizAttemptQuestion;
import com.swp391.OnlineLearning.model.QuizAttemptSelectedOption;
import com.swp391.OnlineLearning.model.dto.AnsweredOption;

import java.util.HashMap;
import java.util.List;

public interface QuizAttemptQuestionService{
    List<QuizAttemptQuestion> findAll();
    QuizAttemptQuestion findById(Long quizAttemptId);
    void save(QuizAttemptQuestion quizAttemptQuestion);
    void delete(QuizAttemptQuestion quizAttemptQuestion);
//    void saveQuestion(Long quizAttemptId, AnsweredOption answeredOption);
//    void removeSelectedOption(Long attemptQuestionId, QuizAttemptSelectedOption attemptSelectedOption);

}
