package com.swp391.OnlineLearning.model;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "answer_options")
@Getter
@Setter
public class AnswerOption extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "NVARCHAR(500)")
    @NotBlank(message = "Ná»™i dung lá»±a chá»n khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng.")
    @Size(max = 500, message = "Ná»™i dung lá»±a chá»n khÃ´ng Ä‘Æ°á»£c vÆ°á»£t quÃ¡ 500 kÃ½ tá»±.")
    private String content; // Ná»™i dung lá»±a chá»n (A, B, C, D...)

    @NotNull(message = "TrÆ°á»ng 'correct' khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng.")
    @Column(nullable = false)
    private Boolean correct; // ÄÃ¡nh dáº¥u Ä‘Ã¢y lÃ  Ä‘Ã¡p Ã¡n Ä‘Ãºng

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String explanation; // Giáº£i thÃ­ch cho lá»±a chá»n nÃ y

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;


    public AnswerOption() {
        super();
    }

    public AnswerOption(String content, Boolean correct, String explanation, Question question) {
        this.content = content;
        this.correct = correct;
        this.explanation = explanation;
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
