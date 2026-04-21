package com.swp391.OnlineLearning.Service.impl;

import com.swp391.OnlineLearning.Repository.ShortAnswerOptionRepository;
import com.swp391.OnlineLearning.Service.ShortAnswerOptionService;
import com.swp391.OnlineLearning.Model.ShortAnswerOption;
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
