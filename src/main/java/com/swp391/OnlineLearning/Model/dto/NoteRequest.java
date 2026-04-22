package com.swp391.OnlineLearning.model.dto;

import jakarta.validation.constraints.NotBlank;

public class NoteRequest {
    @NotBlank(message = "ChÆ°a cÃ³ thá»i gian!")
    private String timeAtLesson;

    @NotBlank(message = "Vui lÃ²ng nháº­p ná»™i dung ghi chÃº!")
    private String content;

    public String getTimeAtLesson() {
        return timeAtLesson;
    }

    public void setTimeAtLesson(String timeAtLesson) {
        this.timeAtLesson = timeAtLesson;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
