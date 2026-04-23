package com.swp391.OnlineLearning.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class QuizAttemptQuestion {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private QuizAttempt quizAttempt;
    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    private boolean bookmarked = false;
    private int questionOrder;
    private Boolean isCorrect = false;

    @OneToMany(mappedBy = "attemptQuestion", cascade = CascadeType.ALL)
    private List<QuizAttemptSelectedOption> selectedOptions;

    public QuizAttemptQuestion(QuizAttempt quizAttempt, Question question, boolean bookmarked, int questionOrder, Boolean isCorrect, List<QuizAttemptSelectedOption> selectedOptions) {
        this.quizAttempt = quizAttempt;
        this.question = question;
        this.bookmarked = bookmarked;
        this.questionOrder = questionOrder;
        this.isCorrect = isCorrect;
        this.selectedOptions = selectedOptions;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }
}

