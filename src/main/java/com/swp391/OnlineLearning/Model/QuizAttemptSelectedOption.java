package com.swp391.OnlineLearning.Model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class QuizAttemptSelectedOption {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private QuizAttemptQuestion attemptQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    private AnswerOption option;

    private String selectedValue; // for fill-in-the-blank

    public QuizAttemptSelectedOption(QuizAttemptQuestion attemptQuestion, AnswerOption option, String selectedValue) {
        this.attemptQuestion = attemptQuestion;
        this.option = option;
        this.selectedValue = selectedValue;
    }

}

