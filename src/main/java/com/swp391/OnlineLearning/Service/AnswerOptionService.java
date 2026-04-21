package com.swp391.OnlineLearning.Service;

import com.swp391.OnlineLearning.Model.AnswerOption;
import com.swp391.OnlineLearning.Model.Question;

public interface AnswerOptionService {
    void save(AnswerOption answerOption);

    void deleteByQuestion(Question questionToUpdate);

    AnswerOption findByAnswerOptionId(Long answerOptionId);

}
