package com.swp391.OnlineLearning.Util;

import com.swp391.OnlineLearning.model.AnswerOption;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class AtLeastOneCorrectAnswerValidator implements ConstraintValidator<AtLeastOneCorrectAnswer, List<AnswerOption>> {

    @Override
    public boolean isValid(List<AnswerOption> answerOptions, ConstraintValidatorContext context) {
        // Náº¿u danh sÃ¡ch lÃ  null hoáº·c rá»—ng, cháº¯c cháº¯n khÃ´ng cÃ³ Ä‘Ã¡p Ã¡n Ä‘Ãºng nÃ o -> khÃ´ng há»£p lá»‡
        if (answerOptions == null || answerOptions.isEmpty()) {
            return false;
        }

        // Sá»­ dá»¥ng Stream API Ä‘á»ƒ kiá»ƒm tra xem cÃ³ báº¥t ká»³ pháº§n tá»­ nÃ o trong list
        // cÃ³ thuá»™c tÃ­nh 'correct' lÃ  true hay khÃ´ng.
        // Ngay khi tÃ¬m tháº¥y má»™t pháº§n tá»­ thá»a mÃ£n, nÃ³ sáº½ tráº£ vá» true ngay láº­p tá»©c.
        return answerOptions.stream().anyMatch(AnswerOption::getCorrect);
    }
}
