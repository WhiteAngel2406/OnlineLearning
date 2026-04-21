package com.swp391.OnlineLearning.Service.impl;

import com.swp391.OnlineLearning.Repository.QuizAttemptSelectedOptionRepository;
import com.swp391.OnlineLearning.Service.QuizAttemptSelectedOptionService;
import com.swp391.OnlineLearning.Model.QuizAttemptSelectedOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QuizAttemptSelectedOptionServiceImpl implements QuizAttemptSelectedOptionService {
    @Autowired
    private QuizAttemptSelectedOptionRepository quizAttemptSelectedOptionRepository;
    public QuizAttemptSelectedOptionServiceImpl(QuizAttemptSelectedOptionRepository quizAttemptSelectedOptionRepository) {
        this.quizAttemptSelectedOptionRepository = quizAttemptSelectedOptionRepository;
    }
    @Override
    public void save(QuizAttemptSelectedOption quizAttemptSelectedOption) {
        quizAttemptSelectedOptionRepository.save(quizAttemptSelectedOption);
    }

    @Override
    public void delete(Long attemptOptionId) {
        quizAttemptSelectedOptionRepository.deleteById(attemptOptionId);
    }

    @Override
    public QuizAttemptSelectedOption findByAttemptOptionId(Long attemptOptionId) {
        return quizAttemptSelectedOptionRepository.findById(attemptOptionId).orElse(null);
    }

    @Override
    public List<QuizAttemptSelectedOption> findAllByQuizAttemptId(Long quizAttemptId) {
        return quizAttemptSelectedOptionRepository.findAll();
    }
}
