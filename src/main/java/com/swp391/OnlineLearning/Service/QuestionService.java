package com.swp391.OnlineLearning.service;

import com.swp391.OnlineLearning.model.Question;

import java.util.List;

public interface QuestionService {
    void save(Question newQuestion);

    Question findById(Long questionId);

    void delete(Question question);

    Question findByIdWithAnswerOptions(Long questionId);

    List<Question> findByQuizIdWithSpecs(Long id, String type);
}
