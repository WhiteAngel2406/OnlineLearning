package com.swp391.OnlineLearning.service;

import com.swp391.OnlineLearning.model.QuizAttemptSelectedOption;

import java.util.List;

public interface QuizAttemptSelectedOptionService {
    void save(QuizAttemptSelectedOption quizAttemptSelectedOption);
    void delete(Long attemptOptionId);
    QuizAttemptSelectedOption findByAttemptOptionId(Long attemptOptionId);
    List<QuizAttemptSelectedOption> findAllByQuizAttemptId(Long quizAttemptId);

}
