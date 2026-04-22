<<<<<<< HEAD:src/main/java/com/swp391/OnlineLearning/Model/QuizAttemptSelectedOption.java
package com.swp391.OnlineLearning.Model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
=======
package com.swp391.OnlineLearning.model;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
>>>>>>> main:src/main/java/com/swp391/OnlineLearning/model/QuizAttemptSelectedOption.java
public class QuizAttemptSelectedOption {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private QuizAttemptQuestion attemptQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    private AnswerOption option;

    private String selectedValue; // for fill-in-the-blank

<<<<<<< HEAD:src/main/java/com/swp391/OnlineLearning/Model/QuizAttemptSelectedOption.java
=======
    public QuizAttemptSelectedOption() {
    }
>>>>>>> main:src/main/java/com/swp391/OnlineLearning/model/QuizAttemptSelectedOption.java
    public QuizAttemptSelectedOption(QuizAttemptQuestion attemptQuestion, AnswerOption option, String selectedValue) {
        this.attemptQuestion = attemptQuestion;
        this.option = option;
        this.selectedValue = selectedValue;
    }

<<<<<<< HEAD:src/main/java/com/swp391/OnlineLearning/Model/QuizAttemptSelectedOption.java
=======
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuizAttemptQuestion getAttemptQuestion() {
        return attemptQuestion;
    }

    public void setAttemptQuestion(QuizAttemptQuestion attemptQuestion) {
        this.attemptQuestion = attemptQuestion;
    }

    public AnswerOption getOption() {
        return option;
    }

    public void setOption(AnswerOption option) {
        this.option = option;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }
>>>>>>> main:src/main/java/com/swp391/OnlineLearning/model/QuizAttemptSelectedOption.java
}

