package com.swp391.OnlineLearning.model;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "short_answer_options")
@Getter
@Setter
public class ShortAnswerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "ÄÃ¡p Ã¡n khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng.")
    @Size(max = 255, message = "ÄÃ¡p Ã¡n khÃ´ng Ä‘Æ°á»£c vÆ°á»£t quÃ¡ 255 kÃ½ tá»±.")
    @Column(nullable = false, columnDefinition = "NVARCHAR(255)")
    private String solutionText; // Ná»™i dung Ä‘Ã¡p Ã¡n Ä‘Ãºng

    @OneToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public ShortAnswerOption() {
        super();
    }

    public ShortAnswerOption(String solutionText, Question question) {
        this.solutionText = solutionText;
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSolutionText() {
        return solutionText;
    }

    public void setSolutionText(String solutionText) {
        this.solutionText = solutionText;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
