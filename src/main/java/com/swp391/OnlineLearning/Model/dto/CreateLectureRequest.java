package com.swp391.OnlineLearning.model.dto;

import com.swp391.OnlineLearning.model.Lesson;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class CreateLectureRequest {
    @NotBlank(message = "Vui lÃ²ng nháº­p tÃªn bÃ i há»c")
    @Size(min = 5, max = 200, message = "TÃªn bÃ i há»c trong khoáº£ng 5-200 kÃ­ tá»±")
    private String title;

    // --- Lecture fields ---
    @NotNull(message = "Vui lÃ²ng nháº­p thá»i lÆ°á»£ng Æ°á»›c tÃ­nh")
    @Positive(message = "Vui lÃ²ng nháº­p thá»i lÆ°á»£ng Æ°á»›c tÃ­nh há»£p lá»‡")
    private Integer estimatedTime;

    @NotBlank(message = "Ná»™i dung bÃ i giáº£ng khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng")
    private String htmlContent;

    private MultipartFile video;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public MultipartFile getVideo() {
        return video;
    }

    public void setVideo(MultipartFile video) {
        this.video = video;
    }
}
