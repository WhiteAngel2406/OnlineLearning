package com.swp391.OnlineLearning.Service;

import com.swp391.OnlineLearning.Model.Question;

import java.util.List;

public interface QuestionService {
    void save(Question newQuestion);

    Question findById(Long questionId);

    void delete(Question question);

    Question findByIdWithAnswerOptions(Long questionId);

    List<Question> findByQuizIdWithSpecs(Long id, String type);
}
