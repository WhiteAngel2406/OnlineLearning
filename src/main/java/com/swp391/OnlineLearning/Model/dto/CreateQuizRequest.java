package com.swp391.OnlineLearning.model.dto;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.NumberFormat;

public class CreateQuizRequest {
    @NotBlank(message = "Vui lÃ²ng nháº­p tÃªn bÃ i há»c")
    @Size(min = 5, max = 200, message = "TÃªn bÃ i há»c trong khoáº£ng tá»« 5-200 kÃ­ tá»±")
    private String title;

    @NotNull(message = "Vui lÃ²ng nháº­p pháº§n trÄƒm Ä‘á»ƒ qua")
    @Min(value = 0, message = "Pháº§n trÄƒm qua pháº£i trong khoáº£ng 0-100")
    @Max(value = 100, message = "Pháº§n trÄƒm qua pháº£i trong khoáº£ng 0-100")
    private Integer passRate;

    @NotNull(message = "Thá»i gian lÃ m bÃ i khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng")
    @Positive(message = "Vui lÃ²ng nháº­p thá»i gian lÃ m bÃ i há»£p lá»‡")
    private Integer timeLimitInMinutes;

    @NotNull(message = "Sá»‘ cÃ¢u há»i khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng")
    @Min(value = 1, message = "Sá»‘ cÃ¢u há»i trong khoáº£ng 1-100")
    @Max(value = 100, message = "Sá»‘ cÃ¢u há»i trong khoáº£ng 1-100")
    private Integer numberOfQuestions;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPassRate() {
        return passRate;
    }

    public void setPassRate(Integer passRate) {
        this.passRate = passRate;
    }

    public Integer getTimeLimitInMinutes() {
        return timeLimitInMinutes;
    }

    public void setTimeLimitInMinutes(Integer timeLimitInMinutes) {
        this.timeLimitInMinutes = timeLimitInMinutes;
    }

    public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(Integer numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }
}
