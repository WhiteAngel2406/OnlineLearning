package com.swp391.OnlineLearning.service.impl;

import com.swp391.OnlineLearning.repository.ShortAnswerOptionRepository;
import com.swp391.OnlineLearning.service.ShortAnswerOptionService;
import com.swp391.OnlineLearning.model.ShortAnswerOption;
import org.springframework.stereotype.Service;

@Service
public class ShortAnswerOptionServiceImpl implements ShortAnswerOptionService {
    private final ShortAnswerOptionRepository shortAnswerOptionRepository;

    public ShortAnswerOptionServiceImpl(ShortAnswerOptionRepository shortAnswerOptionRepository) {
        this.shortAnswerOptionRepository = shortAnswerOptionRepository;
    }


    @Override
    public void save(ShortAnswerOption answerOption) {
        this.shortAnswerOptionRepository.save(answerOption);
    }
}
