package com.swp391.OnlineLearning.service;

import com.swp391.OnlineLearning.model.AnswerOption;
import com.swp391.OnlineLearning.model.Question;

public interface AnswerOptionService {
    void save(AnswerOption answerOption);

    void deleteByQuestion(Question questionToUpdate);

    AnswerOption findByAnswerOptionId(Long answerOptionId);

}
