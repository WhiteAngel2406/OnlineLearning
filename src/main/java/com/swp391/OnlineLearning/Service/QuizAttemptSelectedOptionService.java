package com.swp391.OnlineLearning.Service;

import com.swp391.OnlineLearning.Model.QuizAttemptSelectedOption;

import java.util.List;

public interface QuizAttemptSelectedOptionService {
    void save(QuizAttemptSelectedOption quizAttemptSelectedOption);
    void delete(Long attemptOptionId);
    QuizAttemptSelectedOption findByAttemptOptionId(Long attemptOptionId);
    List<QuizAttemptSelectedOption> findAllByQuizAttemptId(Long quizAttemptId);

}
