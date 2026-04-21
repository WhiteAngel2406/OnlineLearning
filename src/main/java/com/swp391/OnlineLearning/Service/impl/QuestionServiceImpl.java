package com.swp391.OnlineLearning.Service.impl;

import com.swp391.OnlineLearning.Repository.QuestionRepository;
import com.swp391.OnlineLearning.Service.QuestionService;
import com.swp391.OnlineLearning.Service.Specification.QuestionSpecs;
import com.swp391.OnlineLearning.Model.Question;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void save(Question newQuestion) {
        this.questionRepository.save(newQuestion);
    }

    @Override
    public Question findById(Long questionId) {
        return this.questionRepository.findById(questionId).orElseThrow(() -> new IllegalArgumentException("Question not found"));
    }

    @Override
    public void delete(Question question) {
        this.questionRepository.delete(question);
    }

    @Override
    public Question findByIdWithAnswerOptions(Long questionId) {
        return this.questionRepository.findByIdWithAnswerOptions(questionId).orElseThrow(() -> new IllegalArgumentException("Question not found"));
    }

    @Override
    public List<Question> findByQuizIdWithSpecs(Long id, String type) {
        Specification<Question> specs = QuestionSpecs.findByQuizId(id).and(QuestionSpecs.findByType(type));
        return this.questionRepository.findAll(specs);
    }
}
